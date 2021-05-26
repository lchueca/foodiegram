package main.application.service;


import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;

import java.util.List;

public interface DiscoverService {

    //devuelve una lista de Previewpublicaciones de las publicaciones de tus amigos.
    List<PreviewPublicacion> discoverByAmigo();

    //devuelve una lista de Previewpublicaciones con las publicaciones mejores valoradas.
    List<PreviewPublicacion> discoverBestRated(String period) throws IllegalArgumentException;

    //devuelve una lista de Previewpublicaciones con las publicaciones mas valoradas.
    List<PreviewPublicacion> discoverMostRated(String period) throws IllegalArgumentException;

    //devuelve una lista de Previewpublicaciones con las publicaciones de las personas que siguen tus amigos.
    List<UsuarioResource> findFollowedByFriends(Integer userid);

    List<PreviewPublicacion> popularSameCity(Double lat, Double lon);

}
