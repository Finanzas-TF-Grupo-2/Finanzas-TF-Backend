package upc.edu.Finanzas_TF.service;

import upc.edu.Finanzas_TF.model.Compra;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompraService {

    List<Compra> getAllCompras();

    Compra addCompra(Compra compra);

    List<Compra>getCompraByPersonaId(Long personaId);

    Optional<Compra> getCompraById(Long id);

    List<Compra> getCompraByFechaCompraBetween(LocalDate startDate, LocalDate endDate);

    Compra pagarCompra(Long idCompra, double cantidadPagada);
}
