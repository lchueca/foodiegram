package main.application.service;

import main.domain.resource.ComentarioResource;

public interface ComentarioService {

    ComentarioResource editComentario(Integer comID,String text) throws IllegalArgumentException;
    ComentarioResource deleteComentario(Integer comID);

}
