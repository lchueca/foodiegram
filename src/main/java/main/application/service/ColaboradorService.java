package main.application.service;

import main.domain.resource.ColaboradorResource;
import main.rest.forms.CollaborateForm;

public interface ColaboradorService {

     //Se registra un colaborador a partir de un usuario ya registrado ( se le cambia los roles )
    public ColaboradorResource upgradeUser(Integer User, CollaborateForm form);

    public ColaboradorResource getCollab(Integer User);
}
