package main.application.service;

import main.domain.resource.PublicacionResource;

import java.util.List;

public interface DiscoverService {

    List<PublicacionResource> discoverByAmigo(Integer userid);
}
