package main.application.service;

import main.domain.resource.ComentarioResource;

public interface ComentarioService {

    //Devuelve un ComentarioResource  con el mismo comID  y texto modificado.si el text modificado es nulo salta la expection.
    ComentarioResource editComentario(Integer comID,String text) throws IllegalArgumentException;

    //Devuelve un ComentarioResource con el comentario elimina (segun el comID), si el comentario no existe retorna null;
    ComentarioResource deleteComentario(Integer comID);

}
