package main.application.service;

import main.domain.resource.PatrocinioResource;

public interface SponsorService {

    PatrocinioResource getSponsorship(Integer id);
    PatrocinioResource obtain(Integer id, Integer type);
    PatrocinioResource modify(Integer id, Integer type);
}
