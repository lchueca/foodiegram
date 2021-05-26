package main.application.service;

import main.domain.converter.EventoConverter;
import main.domain.resource.EventoResource;
import main.persistence.entity.Evento;
import main.persistence.repository.RepoEvento;
import main.rest.forms.EventForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private EventoConverter converterEvent = new EventoConverter();

    private final Pattern imagePattern = Pattern.compile("\\.+.(png|jpg|jpeg)$");

    @Autowired
    private RepoEvento repoEvent;

    @Value("${apache.rootFolder}")
    private String apacheRootFolder;

    @Value("${apache.address}")
    private String apacheAddress;


    // BUSCAR EVENTO
    //
    // devuelve una lista con los eventos registrados por el colaborador de idcolab que esten activos
    public List<EventoResource> getEventListByIdcolab(Integer idcolab) {

        List<Evento> userList = repoEvent.findByIdcolab(idcolab);
        return userList.stream().map(converterEvent::convert).collect(Collectors.toList());
    }

    // ORGANIZAR EVENTO
    //
    // crea un evento nuevo con los datos proporcionados
    public EventoResource upload(EventForm form) throws IOException, IllegalArgumentException {

        Evento evnt = new Evento(form.getIdCollab(), form.getText(), form.getDate());
        repoEvent.save(evnt);

        if (form.getImage() != null) {
            Matcher matcher = imagePattern.matcher(form.getImage().getOriginalFilename());

            if (!matcher.matches())
                throw new IllegalArgumentException("Only jpeg and png images are supported.");

            try {
                File folder = new File(apacheRootFolder + "/events/" + form.getIdCollab());
                folder.mkdirs();

                String name = folder.getAbsolutePath() + "/" + form.getDate() + "." + matcher.group(1);
                FileOutputStream stream = new FileOutputStream(name);
                stream.write(form.getImage().getBytes());
                stream.close();

                String address = String.format("%s/events/%s/%s.%s", apacheAddress, form.getIdCollab(), form.getDate(), matcher.group(1));
                evnt.setImage(address);
                repoEvent.save(evnt);
            } catch (IOException e) {
                throw e;
            }
        }

        return converterEvent.convert(evnt);
    }

    // MODIFICAR EVENTO
    //
    // modifica un evento existente y activo
    public EventoResource modify(Integer id,EventForm form) throws IOException, IllegalArgumentException {

        // encuentra el evento de id
        Evento evnt = repoEvent.findById(id);
        // se supone que siempre se encuentra porque en la seleccion del evento se muestran los existente
        // por lo que no hace falta controlar una excepcion de si es null

        if (form.getText() != null) evnt.setText(form.getText());
        if (form.getImage() != null) {
            Matcher matcher = imagePattern.matcher(form.getImage().getOriginalFilename());

            if (!matcher.matches())
                throw new IllegalArgumentException("Only jpeg and png images are supported.");

            try {
                File folder = new File(apacheRootFolder + "/events/" + evnt.getIdcolab());
                folder.mkdirs();

                String name = folder.getAbsolutePath() + "/" + form.getDate() + "." + matcher.group(1);
                FileOutputStream stream = new FileOutputStream(name);
                stream.write(form.getImage().getBytes());
                stream.close();

                String address = String.format("%s/events/%s/%s.%s", apacheAddress, evnt.getIdcolab(), form.getDate() , matcher.group(1));
                evnt.setImage(address);
            } catch (IOException e) {
                throw e;
            }
        }
        if (form.getDate() != null) evnt.setEndtime(form.getDate());
        repoEvent.save(evnt);

        return converterEvent.convert(evnt);
    }

    // ELIMINAR EVENTO
    //
    // devuelve true si consigue eliminar el evento sino false
    public boolean delete(Integer id) {
        Evento evnt = repoEvent.findById(id);

        if (evnt != null) {
            repoEvent.delete(evnt);
            return true;
        }

        return false;
    }
}
