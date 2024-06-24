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

        // Verifica que la cantidad pagada no sea mayor que el monto final de la compra
        if (cantidadPagada > compra.getMontoFinal()) {
            throw new RuntimeException("La cantidad pagada no puede ser mayor que el monto final de la compra");
        }


        // Si la compra es en cuotas, decrementa el número de cuotas restantes
        if (compra.isPagoEnCuotas()) {

            compra.setFechaPago(compra.getFechaPago().plusMonths(1));
            compra.setNumeroCuotas(compra.getNumeroCuotas()-1);
            compra.setMontoFinal(compra.getMontoFinal() - compra.getMontoCuotaFinal());

        }else {
            // Si la compra no es en cuotas, decrementa el monto final de la compra
            compra.setMontoFinal(compra.getMontoFinal() - cantidadPagada);
        }
        compra.setPaid(compra.getMontoFinal() == 0);


        // Guarda la compra actualizada en la base de datos y devuélvela
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
        double montoFinal;

        LocalDate fechaCompra = compra.getFechaCompra();
        LocalDate fechaPagoInicial = fechaCompra.plusMonths(1).withDayOfMonth(credito.getDiaPago());
        compra.setFechaPago(fechaPagoInicial);

        if (compra.isPagoEnCuotas()) {
            // Si la compra se realiza en cuotas, realizar los cálculos correspondientes
            double montoCuotaFinal = calcularPagoMensualEnCuotas(montoProducto, credito.getPorcentajeTasa(), compra.getNumeroCuotas());
            BigDecimal montoCuotaFinalBD = new BigDecimal(montoCuotaFinal).setScale(2, RoundingMode.HALF_UP);
            compra.setMontoCuotaFinal(montoCuotaFinalBD.doubleValue());
            montoFinal = montoCuotaFinal * compra.getNumeroCuotas();



        } else {
            int frecuenciaPagoDias = Frecuencia.fromString(credito.getFrecuenciaPago()).getDias();

            int diaPago = credito.getDiaPago();
            if ("nominal".equalsIgnoreCase(credito.getTipoTasa())) {
                // Calcular monto final con tasa nominal
                int capitalizacionDias = Frecuencia.fromString(credito.getCapitalizacion()).getDias();
                double m = (double) frecuenciaPagoDias / capitalizacionDias;
                double n = (double) diaPago / capitalizacionDias;
                montoFinal = montoProducto * Math.pow((1 + credito.getPorcentajeTasa() / m), n);
            } else if ("efectiva".equalsIgnoreCase(credito.getTipoTasa())) {
                // Calcular monto final con tasa efectiva
                double t = credito.getPorcentajeTasa();
                double n = (double) diaPago / frecuenciaPagoDias;
                montoFinal = montoProducto * Math.pow((1 + t), n);
            } else {
                throw new IllegalArgumentException("Tipo de tasa desconocido: " + credito.getTipoTasa());
            }
        }

        BigDecimal montoFinalBD = new BigDecimal(montoFinal).setScale(2, RoundingMode.HALF_UP);
        return montoFinalBD.doubleValue();
    }

    private double calcularPagoMensualEnCuotas(double montoProducto, double tasaEfectiva, int numeroCuotas) {
        return montoProducto * ((tasaEfectiva * Math.pow(1 + tasaEfectiva, numeroCuotas)) / (Math.pow(1 + tasaEfectiva, numeroCuotas) - 1));
    }



}
