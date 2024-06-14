package upc.edu.Finanzas_TF.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.Finanzas_TF.model.Producto;
import upc.edu.Finanzas_TF.repository.ProductoRepository;
import upc.edu.Finanzas_TF.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getAllProductos() {
        return new ResponseEntity<List<Producto>>(productoService.getAllProductos(), HttpStatus.OK);
    }

    @GetMapping("/productos/nombre")
    public ResponseEntity<List<Producto>> getProductoByNombre(String nombre) {
        return new ResponseEntity<List<Producto>>(productoService.getProductoByNombre(nombre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producto> addProducto(@RequestBody Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Return a 400 Bad Request if 'nombre' is null or empty
        }
        if (producto.getCosto() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Return a 400 Bad Request if 'costo' is less than or equal to 0
        }

        Producto newProducto = productoService.addProducto(producto);
        return new ResponseEntity<>(newProducto, HttpStatus.CREATED);

    }
}
