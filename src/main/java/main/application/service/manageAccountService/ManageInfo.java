package main.application.service.manageAccountService;

import main.domain.resource.UsuarioResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ManageInfo {

    //Cambia el nombre del usuario idUser, retorna null si no existe el usuario con iduser y una excepción si ya hay un usuario con nombre newName
    UsuarioResource changeName(Integer idUser, String newName);

    //Cambia la contraseña del usuario idUser, retorna null si no existe el usuario con idUser
    UsuarioResource changePasswd(Integer idUser, String newPasswd);

    //Cambia el email del usuario idUser, retorna null si no existe el usuario con idUser
    UsuarioResource changeEmail(Integer idUser, String newMail);

    //Cambia la foto de perfil del usuario idUser, retorn null si no existe el usuario con idUser
    UsuarioResource changeProfilePicture(Integer idUser, MultipartFile newProfilePic) throws IOException;
}
