package upc.edu.Finanzas_TF.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.Finanzas_TF.exception.ResourceNotFoundException;
import upc.edu.Finanzas_TF.model.Persona;
import upc.edu.Finanzas_TF.repository.PersonaRepository;
import upc.edu.Finanzas_TF.service.PersonaService;

import java.util.List;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    private final PersonaRepository personaRepository;

    public PersonaController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @GetMapping("/personas")
    public ResponseEntity<List<Persona>> getAllPersonas() {
        return new ResponseEntity<List<Persona>>(personaService.getAllPersonas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        return new ResponseEntity<Persona>(personaService.getPersona(id), HttpStatus.OK);
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<Persona>> getPersonaByNombre(@RequestParam String nombre) {
        return new ResponseEntity<List<Persona>>(personaService.getPersonaByNombre(nombre), HttpStatus.OK);

    }

    @PostMapping("/personas")
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        return new ResponseEntity<Persona>(personaService.createPersona(persona), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona persona) {
        return new ResponseEntity<Persona>(personaService.updatePersona(id, persona), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long id) {
        personaService.deletePersona(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
