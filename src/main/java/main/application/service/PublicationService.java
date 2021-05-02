package main.application.service;

import main.domain.resource.ComentarioResource;
import main.domain.resource.PublicacionResource;
import main.domain.resource.ValoracionResource;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface PublicationService {

    // Posts
    PublicacionResource getPost(Integer pubID);
    PublicacionResource editPost(Integer pubID, String text, String loc) throws IllegalArgumentException;
    PublicacionResource deletePost(Integer pubID);

    // Valoraciones
    List<ValoracionResource> getRatings(Integer pubID);
    ValoracionResource setRating(Integer pubID, Integer user, Integer score) throws IllegalArgumentException, DataIntegrityViolationException;
    ValoracionResource getRating(Integer pubID, Integer user);
    ValoracionResource deleteRating(Integer pubID, Integer user);

    // Comentarios
    List<ComentarioResource> getComments(Integer pubID);
    ComentarioResource setComment(Integer pubID, Integer userID, String text) throws DataIntegrityViolationException;

}
