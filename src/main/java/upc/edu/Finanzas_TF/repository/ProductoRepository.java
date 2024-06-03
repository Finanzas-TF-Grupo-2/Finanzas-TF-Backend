package upc.edu.Finanzas_TF.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.Finanzas_TF.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto>findByNombreContaining(String nombre);
}
