package main.application.service;

import main.domain.resource.MensajeResource;
import org.springframework.dao.DataIntegrityViolationException;

import javax.naming.NoPermissionException;
import java.util.List;

public interface MensajeService {

    //Devuelve el MensajeResource (buscado por el mensID), si el mensaje no existe retorna null.
    MensajeResource getMensaje(Integer mensID, Integer loggedInUserId) throws NoPermissionException;


    //Devuelve el MesajeResource con el mensaje que se elimina (buscado por el mensID),si el mensaje no existe retorna null.
    MensajeResource deleteMensaje(Integer mensID, Integer loggedInUserId) throws NoPermissionException;

       //Devuelve un MensajeResource con el mensaje posteado, si alguno de los dos usuarios no exiten salta la exeption.
    MensajeResource setMensaje(Integer user1ID, String username2, String mensaje) throws IllegalArgumentException;

    //Devuelve una lista de MensajeResource con los mensajes que ha enviado el user1ID.
    List<MensajeResource> getMensajes(Integer userID);



}
