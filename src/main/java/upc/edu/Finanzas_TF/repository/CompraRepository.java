package upc.edu.Finanzas_TF.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.Finanzas_TF.model.Compra;

import java.time.LocalDate;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra>findByPersonaId(Long persona);

    List<Compra> findByFechaCompraBetween(LocalDate startDate, LocalDate endDate);
}
