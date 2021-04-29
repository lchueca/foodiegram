package main.application.service;

import java.util.List;

import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.domain.resource.ValoracionResource;
import main.persistence.proyecciones.PreviewPublicacion;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface UserService  {

    List<PreviewPublicacion> getPosts(Integer user);
    PublicacionResource update(Integer user, String text, String loc, MultipartFile image);
    List<ValoracionResource> getRatings(Integer user);

    UsuarioResource register(String user, String passwd, String email);


}
