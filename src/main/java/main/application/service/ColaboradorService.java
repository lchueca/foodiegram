package main.application.service;

import main.domain.resource.ColaboradorResource;

public interface ColaboradorService {


    public ColaboradorResource upgradeUser(Integer User,String origin,String type,String localization);



}
