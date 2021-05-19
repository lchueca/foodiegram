package main.application.service;

import main.domain.resource.EventoResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {

    List<EventoResource> getEventListByIdcolab(Integer idcolab);
    EventoResource upload(Integer idcolab, String text, MultipartFile image, String date) throws IOException, IllegalArgumentException;
    EventoResource modify(Integer id, String Text, MultipartFile image, String date) throws IOException, IllegalArgumentException;
    boolean delete(Integer id);
}
