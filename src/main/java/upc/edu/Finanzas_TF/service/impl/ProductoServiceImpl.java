package upc.edu.Finanzas_TF.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.Finanzas_TF.model.Producto;
import upc.edu.Finanzas_TF.repository.ProductoRepository;
import upc.edu.Finanzas_TF.service.ProductoService;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto addProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> getProductoByNombre(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }

    @Override
    public Producto deleteProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
        return producto;

    }
}
