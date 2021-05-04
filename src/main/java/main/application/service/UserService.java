package main.application.service;

import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.domain.resource.ValoracionResource;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface UserService  {

    // Devuelve la info del usuario, o null si el usuario no existe.
    UsuarioResource getUserByName(String user);

    // Devuelve una lista con todas las publicaciones (id e imagen) del usuario, o null si el usuario no existe.
    List<PreviewPublicacion> getPosts(String user);

    // Sube una publicacion. Devuelve la propia publicacion si ha habido exito, null si el usuario no existe.
    // Lanza excepcion si el usuario no existe, o si no se puede guardar la imagen.
    // Con este vais a tener problemas haciendo pruebas, yo pasaria de él.
    public PublicacionResource upload(String user, String text, String loc, MultipartFile image) throws IOException, IllegalArgumentException;

    // Devuelve todoas las valoraciones que ha hecho un usuario (En todas las publicaciones), o null si el usuario no existe.
    List<ValoracionResource> getRatings(String user);

    // Da de alta un usuario en la BD y le envia un mail de confirmacion.
    // Lanza una excepcion si se le introduce un usuario, contraseña o email no validos.
    // Si se ha podido registrar al usuario, lo devuelve.
    UsuarioResource register(String user, String passwd, String email) throws IllegalArgumentException;

    // Sudad de este.
    UsuarioResource verify(Integer token);

}
