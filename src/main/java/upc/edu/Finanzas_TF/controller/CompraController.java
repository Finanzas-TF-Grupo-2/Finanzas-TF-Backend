package upc.edu.Finanzas_TF.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.Finanzas_TF.model.Compra;
import upc.edu.Finanzas_TF.service.CompraService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping("/compras")
    public ResponseEntity<List<Compra>> getAllCompras() {
        return new ResponseEntity<List<Compra>>(compraService.getAllCompras(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompraById(@PathVariable Long id) {
        return new ResponseEntity<Compra>(compraService.getCompraById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<List<Compra>> getCompraByPersonaId(@PathVariable Long personaId) {
        return new ResponseEntity<List<Compra>>(compraService.getCompraByPersonaId(personaId), HttpStatus.OK);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Compra>> getCompraByFechaCompraBetween(@RequestParam LocalDate startDate, LocalDate endDate) {
        return new ResponseEntity<List<Compra>>(compraService.getCompraByFechaCompraBetween(startDate, endDate), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Compra> addCompra(@RequestBody Compra compra) {
        return new ResponseEntity<Compra>(compraService.addCompra(compra), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<?> pagarCompra(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double montoFinal = request.get("montoFinal");
        compraService.pagarCompra(id, montoFinal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompra(@PathVariable Long id) {
        compraService.deleteCompra(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
