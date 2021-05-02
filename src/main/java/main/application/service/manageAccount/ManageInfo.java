package main.application.service.manageAccount;

import main.domain.resource.UsuarioResource;

public interface ManageInfo {

    UsuarioResource changeName(Integer idUser, String newName);
    UsuarioResource changePasswd(Integer idUser, String newPasswd);
    UsuarioResource changeEmail(Integer idUser, String newMail);
    UsuarioResource changeProfilePicture(Integer idUser, String newProfilePic);
}
