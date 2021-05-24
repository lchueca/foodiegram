package main.application.service;

import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;

import java.util.List;

public interface DiscoverService {

    List<PublicacionResource> discoverByAmigo(Integer userid);
    List<UsuarioResource> findFollowedByFriends(Integer userid);
}
