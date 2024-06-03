package upc.edu.Finanzas_TF.service;

import upc.edu.Finanzas_TF.model.Persona;

import java.util.List;

public interface PersonaService {


    Persona createPersona(Persona persona);

    Persona getPersona(Long personaId);

    List<Persona> getAllPersonas();

    Persona updatePersona(Long personaId, Persona personaRequest);

    List<Persona> getPersonaByNombre(String nombre);

    void deletePersona(Long personaId);





}
