package upc.edu.Finanzas_TF.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.Finanzas_TF.model.Compra;
import upc.edu.Finanzas_TF.model.Credito;
import upc.edu.Finanzas_TF.model.Persona;
import upc.edu.Finanzas_TF.model.Producto;
import upc.edu.Finanzas_TF.repository.CompraRepository;
import upc.edu.Finanzas_TF.repository.PersonaRepository;
import upc.edu.Finanzas_TF.service.CompraService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    @Override
    public Compra addCompra(Compra compra) {
        Persona persona = compra.getPersona();
        Persona existePersona = personaRepository.findById(persona.getId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        compra.setPersona(existePersona);
        Producto producto = compra.getProducto();
        compra.setProducto(producto);
        double montoFinal = calcularMontoFinal(compra);
        compra.setMontoFinal(montoFinal);
        double personaSaldoFinal = existePersona.getCredito().getSaldo() - montoFinal;
        existePersona.getCredito().setSaldo(personaSaldoFinal);
        return compraRepository.save(compra);
    }

    @Override
    public List<Compra> getCompraByPersonaId(Long personaId) {
        return compraRepository.findByPersonaId(personaId);
    }

    @Override
    public Optional<Compra> getCompraById(Long id) {
        return compraRepository.findById(id);
    }





    @Override
    public List<Compra> getCompraByFechaCompraBetween(LocalDate  startDate, LocalDate  endDate) {
        return compraRepository.findByFechaCompraBetween(startDate,endDate);
    }

    @Override
    public Compra pagarCompra(Long idCompra, double cantidadPagada) {
        Compra compra = compraRepository.findById(idCompra)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        BigDecimal montoFinal = BigDecimal.valueOf(compra.getMontoFinal());
        BigDecimal montoCuotaFinal = BigDecimal.valueOf(compra.getMontoCuotaFinal());
        BigDecimal cantidadPagadaBD = BigDecimal.valueOf(cantidadPagada);



        if (cantidadPagadaBD.compareTo(montoFinal) > 0) {
            throw new RuntimeException("La cantidad pagada no puede ser mayor que el monto final de la compra");
        }



        if (compra.isPagoEnCuotas()) {

            compra.setFechaPago(compra.getFechaPago().plusMonths(1));
            compra.setNumeroCuotas(compra.getNumeroCuotas()-1);
            montoFinal = montoFinal.subtract(montoCuotaFinal);

        }else {

            montoFinal = montoFinal.subtract(cantidadPagadaBD);
        }


        compra.setMontoFinal(montoFinal.setScale(2, RoundingMode.HALF_UP).doubleValue());
        compra.setPaid(montoFinal.compareTo(BigDecimal.ZERO) == 0);



        return compraRepository.save(compra);
    }

    @Override
    public Compra deleteCompra(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        compraRepository.delete(compra);
        return compra;
    }

    private double calcularMontoFinal(Compra compra) {
        double montoProducto = compra.getProducto().getCosto();
        Persona persona = compra.getPersona();
        Credito credito = persona.getCredito();
        BigDecimal montoFinal;

        LocalDate fechaCompra = compra.getFechaCompra();
        LocalDate fechaPagoInicial = fechaCompra.plusMonths(1).withDayOfMonth(credito.getDiaPago());
        compra.setFechaPago(fechaPagoInicial);

        if (compra.isPagoEnCuotas()) {
            // Si la compra se realiza en cuotas, realizar los cálculos correspondientes
            BigDecimal montoCuotaFinal = BigDecimal.valueOf(calcularPagoMensualEnCuotas(montoProducto, credito.getPorcentajeTasa(), compra.getNumeroCuotas()));
            montoCuotaFinal = montoCuotaFinal.setScale(2, RoundingMode.HALF_UP);
            compra.setMontoCuotaFinal(montoCuotaFinal.doubleValue());
            montoFinal = montoCuotaFinal.multiply(BigDecimal.valueOf(compra.getNumeroCuotas()));



        } else {
            int frecuenciaPagoDias = Frecuencia.fromString(credito.getFrecuenciaPago()).getDias();

            int diaPago = credito.getDiaPago();
            double montoProductoFinal;
            if ("nominal".equalsIgnoreCase(credito.getTipoTasa())) {
                // Calcular monto final con tasa nominal
                int capitalizacionDias = Frecuencia.fromString(credito.getCapitalizacion()).getDias();
                double m = (double) frecuenciaPagoDias / capitalizacionDias;
                double n = (double) diaPago / capitalizacionDias;
                montoProductoFinal = montoProducto * Math.pow((1 + credito.getPorcentajeTasa() / m), n);
            } else if ("efectiva".equalsIgnoreCase(credito.getTipoTasa())) {
                // Calcular monto final con tasa efectiva
                double t = credito.getPorcentajeTasa();
                double n = (double) diaPago / frecuenciaPagoDias;
                montoProductoFinal = montoProducto * Math.pow((1 + t), n);
            } else {
                throw new IllegalArgumentException("Tipo de tasa desconocido: " + credito.getTipoTasa());
            }
            montoFinal = BigDecimal.valueOf(montoProductoFinal);
        }

        return montoFinal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private double calcularPagoMensualEnCuotas(double montoProducto, double tasaEfectiva, int numeroCuotas) {
        BigDecimal montoProductoBD = BigDecimal.valueOf(montoProducto);
        BigDecimal tasaEfectivaBD = BigDecimal.valueOf(tasaEfectiva);
        BigDecimal uno = BigDecimal.ONE;
        BigDecimal pow = uno.add(tasaEfectivaBD).pow(numeroCuotas);
        BigDecimal numerador = tasaEfectivaBD.multiply(pow);
        BigDecimal denominador = pow.subtract(uno);
        BigDecimal cuota = montoProductoBD.multiply(numerador).divide(denominador, 2, RoundingMode.HALF_UP);
        return cuota.doubleValue();
    }



}
