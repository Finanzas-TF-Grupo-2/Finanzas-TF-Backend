package upc.edu.Finanzas_TF.service;

import upc.edu.Finanzas_TF.model.Credito;

import java.util.List;
import java.util.Optional;

public interface CreditoService {

    List<Credito> getCreditoByPersonaId(Long personaId);

    //Credito createCredito(Credito credito);

    List<Credito> getAllCreditos();

    Optional<Credito> getCreditoById(Long id);

}
