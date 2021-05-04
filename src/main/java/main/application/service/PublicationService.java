package main.application.service;

import main.domain.resource.ComentarioResource;
import main.domain.resource.PublicacionResource;
import main.domain.resource.ValoracionResource;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface
PublicationService {

    // POST

    // Devuelve la publicaion, o null si no existe.
    PublicacionResource getPost(Integer pubID);

    // Para cambiar el texto lo la localizacion de una publicacion.
    // Devuelve la misma publicacion tras haberse aplicado el cambio.
    // Lanza excepcion si tanto text como loc son nulos.
    PublicacionResource editPost(Integer pubID, String text, String loc) throws IllegalArgumentException;

    // Elimina una publicaicon.
    // Devuelve la publicacion eliminada, o null si no existe.
    PublicacionResource deletePost(Integer pubID);

    // VALORACIONES

    // Devuelve una lista de ValoracionResource con todas las valoraciones de una publicacion, retorna null si la publicacion no existe.
    List<ValoracionResource> getRatings(Integer pubID);

    // Devuelve un ValoracionResource con la valoracion posteada, salta una exception si el usuario no existe o si el la puntuacion es menor que 0 y mayor que 5.
    ValoracionResource setRating(Integer pubID, String user, Float score) throws IllegalArgumentException;

    //Devuelve  un ValoracionResource con la valoracion de un usuario dentro de una publicacion, retorna null si el usuario no existe.
    ValoracionResource getRating(Integer pubID, String user) ;

    //Devuelve un ValoracionResource con la valoracion eliminada,retorna null si el usuario no existe.
    ValoracionResource deleteRating(Integer pubID, String user);

    // COMENTARIOS

    // Devuelve la lista de comentarios de una foto, o null si la publicacion no existe.
    List<ComentarioResource> getComments(Integer pubID);

    // Añade o cambia un el comentario de un usuario en una publicacion.
    // Devuelve el mismo comentario tras haberse aplicado el cambio.
    // Lanza excepcion si el texto es null o vacio, o si el usuario no existe.
    ComentarioResource setComment(Integer pubID, String userID, String text) throws IllegalArgumentException;

}
