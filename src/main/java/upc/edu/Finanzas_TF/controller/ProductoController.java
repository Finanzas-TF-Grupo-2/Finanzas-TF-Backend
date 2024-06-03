package upc.edu.Finanzas_TF.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/productos")
    public ResponseEntity<Producto> addProducto(Producto producto) {
        return new ResponseEntity<Producto>(productoService.addProducto(producto), HttpStatus.CREATED);
    }




}
