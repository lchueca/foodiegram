package main.application.service;

import main.domain.resource.PatrocinioResource;

public interface SponsorService {

    PatrocinioResource getSponsorship(Integer id);
    PatrocinioResource obtain(Integer id, Integer type, Float money);
    PatrocinioResource modify(Integer id, Integer type, Float money);
}
