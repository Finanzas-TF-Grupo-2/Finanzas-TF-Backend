package upc.edu.Finanzas_TF.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.Finanzas_TF.model.Credito;

import java.util.List;

public interface CreditoRepository extends JpaRepository<Credito, Long> {

    List<Credito>findByPersonaId(Long personaId);
}
