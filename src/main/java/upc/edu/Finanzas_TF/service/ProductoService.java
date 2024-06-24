package upc.edu.Finanzas_TF.service;

import upc.edu.Finanzas_TF.model.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> getAllProductos();

    Producto addProducto(Producto producto);

    List<Producto> getProductoByNombre(String nombre);

    Producto deleteProducto (Long id);
}
