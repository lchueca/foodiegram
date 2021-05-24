package main.application.service;

import com.sun.javaws.exceptions.InvalidArgumentException;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;

import java.util.List;

public interface DiscoverService {

    List<PreviewPublicacion> discoverByAmigo(Integer userid);
    List<PreviewPublicacion> discoverBestRated(String period) throws IllegalArgumentException;
    List<PreviewPublicacion> discoverMostRated(String period) throws IllegalArgumentException;

    List<UsuarioResource> findFollowedByFriends(Integer userid);

    List<PreviewPublicacion> popularSameCity(Double lat, Double lon);

}
