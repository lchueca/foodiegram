package main.application.service;

import main.domain.resource.MensajeResource;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface MensajeService {

    MensajeResource getMensaje(Integer mensID);
    MensajeResource deleteMensaje(Integer mensID);
    List<MensajeResource> getMensajes(Integer user1ID);
    MensajeResource setMensaje(Integer user1ID, Integer user2ID, String mensaje) throws DataIntegrityViolationException;

}
