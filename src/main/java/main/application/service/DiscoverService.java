package main.application.service;

import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;

import java.util.List;

public interface DiscoverService {

    List<PreviewPublicacion> discoverByAmigo(Integer userid);
    List<PreviewPublicacion> discoverByPopularity();

    List<UsuarioResource> findFollowedByFriends(Integer userid);

}
