package main.application.service;

import main.domain.resource.EventoResource;
import main.domain.resource.MeetupResource;
import main.rest.forms.EventForm;

import java.io.IOException;
import java.util.List;

public interface EventService {

    // BUSCAR EVENTO
    //
    // devuelve una lista con los eventos registrados por el colaborador de idcolab que esten activos
    List<EventoResource> getEventListByIdcolab(Integer idcolab);

    // ORGANIZAR EVENTO
    //
    // crea un evento nuevo con los datos proporcionados
    EventoResource upload(EventForm form) throws IOException, IllegalArgumentException;

    // MODIFICAR EVENTO
    //
    // modifica un evento existente y activo
    EventoResource modify(Integer id, EventForm form) throws IOException, IllegalArgumentException;

    // ELIMINAR EVENTO
    //
    // devuelve true si consigue eliminar el evento sino false
    boolean delete(Integer id);

    //UNIRSE A UN EVENTO
    //
    //devuelve un Meetupresource con el evento y el usuario que se unio.
    MeetupResource joinEvent(Integer userid, Integer eventID);

    //SALIR DE UN EVENTO
    //
    //devuelve el MeetupResource eliminado.
    MeetupResource leaveEvent(Integer userid, Integer eventID);
}
