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
    ValoracionResource setRating(Integer pubID, String user, Integer score) throws IllegalArgumentException;
    ValoracionResource getRating(Integer pubID, String user) throws IllegalArgumentException;
    ValoracionResource deleteRating(Integer pubID, String user) throws IllegalArgumentException;

    // Comentarios
    List<ComentarioResource> getComments(Integer pubID);
    ComentarioResource setComment(Integer pubID, String userID, String text) throws IllegalArgumentException;



}
