package main.application.service;

import main.domain.resource.PublicacionResource;

import java.util.List;

public interface DiscoverService {

   public  List<PublicacionResource> discoverByAmigo(Integer userid);
   public  List<PublicacionResource> discoverByPopularity();
}
