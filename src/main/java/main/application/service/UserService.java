package main.application.service;

import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.domain.resource.ValoracionResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService  {

    UsuarioResource getUserByName(String user);
    List<PreviewPublicacion> getPosts(String user);
    public PublicacionResource upload(String user, String text, String loc, MultipartFile image) throws IOException, IllegalArgumentException;
    List<ValoracionResource> getRatings(String user);


    UsuarioResource register(String user, String passwd, String email) throws IllegalArgumentException;

    public UsuarioResource verify(Integer token) throws IllegalArgumentException;

}
