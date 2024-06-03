package upc.edu.Finanzas_TF.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.Finanzas_TF.model.Credito;
import upc.edu.Finanzas_TF.model.Persona;
import upc.edu.Finanzas_TF.repository.CreditoRepository;
import upc.edu.Finanzas_TF.repository.PersonaRepository;
import upc.edu.Finanzas_TF.service.CreditoService;

import java.util.List;
import java.util.Optional;

@Service
public class CreditoServiceImpl implements CreditoService {

    @Autowired
    private CreditoRepository creditoRepository;
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Credito> getCreditoByPersonaId(Long personaId) {
        return creditoRepository.findByPersonaId(personaId);
    }

    /*@Override
    public Credito createCredito(Credito credito) {
        credito.setPorcentajeTasa(credito.getPorcentajeTasa() / 100);
        Persona persona = personaRepository.findById(credito.getPersona().getId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        credito.setPersona(persona);

        return creditoRepository.save(credito);
    }*/

    @Override
    public List<Credito> getAllCreditos() {
        return creditoRepository.findAll();
    }

    @Override
    public Optional<Credito> getCreditoById(Long id) {
        return creditoRepository.findById(id);
    }
}
