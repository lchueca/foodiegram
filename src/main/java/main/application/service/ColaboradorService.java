package main.application.service;

import main.domain.resource.ColaboradorResource;
import main.rest.forms.CollaborateForm;

public interface ColaboradorService {


    public ColaboradorResource upgradeUser(Integer User, CollaborateForm form);



}
