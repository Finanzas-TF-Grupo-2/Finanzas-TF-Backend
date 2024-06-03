package upc.edu.Finanzas_TF.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.Finanzas_TF.exception.ResourceNotFoundException;
import upc.edu.Finanzas_TF.model.Credito;
import upc.edu.Finanzas_TF.model.Persona;
import upc.edu.Finanzas_TF.repository.PersonaRepository;
import upc.edu.Finanzas_TF.service.PersonaService;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Persona createPersona(Persona persona) {
        Credito credito = persona.getCredito();
        if (credito != null) {
            persona.setCredito(credito);
            credito.setPorcentajeTasa(credito.getPorcentajeTasa() / 100);
        }

        return personaRepository.save(persona);
    }

    @Override
    public Persona getPersona(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
    }

    @Override
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public Persona updatePersona(Long id, Persona personaDetails) {
        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
        persona.setNombre(personaDetails.getNombre());
        persona.setDireccion(personaDetails.getDireccion());
        persona.setTelefono(personaDetails.getTelefono());
        persona.setCorreo(personaDetails.getCorreo());
        persona.setDni(personaDetails.getDni());
        return personaRepository.save(persona);
    }

    @Override
    public List<Persona> getPersonaByNombre(String nombre) {
        return personaRepository.findByNombreContaining(nombre);
    }

    @Override
    public void deletePersona(Long personaId) {
        personaRepository.deleteById(personaId);


    }
}
