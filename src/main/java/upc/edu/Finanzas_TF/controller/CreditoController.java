package upc.edu.Finanzas_TF.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.Finanzas_TF.model.Credito;
import upc.edu.Finanzas_TF.repository.CreditoRepository;
import upc.edu.Finanzas_TF.service.CreditoService;

import java.util.List;


@RestController
@RequestMapping("/api/credito")
public class CreditoController {

    @Autowired
    private CreditoService creditoService;

    private final CreditoRepository creditoRepository;

    public CreditoController(CreditoRepository creditoRepository) {
        this.creditoRepository = creditoRepository;
    }

    @GetMapping("/creditos")
    public ResponseEntity<List<Credito>> getAllCreditos() {
        return new ResponseEntity<List<Credito>>(creditoService.getAllCreditos(), HttpStatus.OK);
    }

    @GetMapping("/creditos/{id}")
    public ResponseEntity<Credito> getCreditoById(Long id) {
        return new ResponseEntity<Credito>(creditoService.getCreditoById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/creditos/persona/{personaId}")
    public ResponseEntity<List<Credito>> getCreditoByPersonaId(Long personaId) {
        return new ResponseEntity<List<Credito>>(creditoService.getCreditoByPersonaId(personaId), HttpStatus.OK);
    }

    /*@PostMapping("/creditos")
    public ResponseEntity<Credito> createCredito(@RequestBody Credito credito) {
        return new ResponseEntity<Credito>(creditoService.createCredito(credito), HttpStatus.CREATED);

    }*/

}
