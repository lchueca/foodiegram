package main.application.service;

import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.domain.resource.ValoracionResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService  {

    UsuarioResource getUser(Integer user);
    List<PreviewPublicacion> getPosts(Integer user);
    PublicacionResource upload(Integer user, String text, String loc, MultipartFile image) throws IOException;
    List<ValoracionResource> getRatings(Integer user);


    UsuarioResource register(String user, String passwd, String email) throws IllegalArgumentException;

    UsuarioResource verify(Integer token)throws IllegalArgumentException;

}
