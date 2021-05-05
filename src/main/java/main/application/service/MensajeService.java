package main.application.service;

import main.domain.resource.MensajeResource;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface MensajeService {

    //Devuelve el MensajeResource (buscado por el mensID), si el mensaje no existe retorna null.
    MensajeResource getMensaje(Integer mensID);

    //Devuelve el MesajeResource con el mensaje que se elimina (buscado por el mensID),si el mensaje no existe retorna null.
    MensajeResource deleteMensaje(Integer mensID);



    //Devuelve un MensajeResource con el mensaje posteado, si alguno de los dos usuarios no exiten salta la exeption.
    MensajeResource setMensaje(String user1ID, String user2ID, String mensaje) throws IllegalArgumentException;

}
