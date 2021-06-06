package main.application.service.manageAccountService;

import main.domain.resource.UsuarioResource;

public interface Unsubscribe {

    //Elimina el usuario con idUser
    UsuarioResource unsubscribe(Integer userId);
}
