package upc.edu.Finanzas_TF.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.Finanzas_TF.model.Persona;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    List<Persona>findByNombreContaining(String nombre);
}
