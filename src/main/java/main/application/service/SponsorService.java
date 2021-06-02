package main.application.service;

import main.domain.resource.PatrocinioResource;

public interface SponsorService {




    // devuelve un patrocinio existente o null en caso contrario
    PatrocinioResource getSponsorship(Integer id);
    // CREA/MODIFICA UN PATROCINIO
    //
    // crea un nuevo patrocinio
    // devuelve un patrocinio creado
    PatrocinioResource obtain(Integer id, Integer type, Float money);
    // MODIFICAR PATROCINIO
    //
    // amplia el tiempo de patrocinio
    // devuelve un patrocinio modificado
    PatrocinioResource modify(Integer id, Integer type, Float money);

}
