package main.application.service.manageAccountService;


import main.domain.resource.AmigoResource;
import main.domain.resource.PreviewPublicacion;

import java.util.List;

public interface ManageFriends {

    //El usuario con id añade un usario de nombre name, devuelve null en caso de fallo
    AmigoResource addFriend(Integer id, String name);

    //El usuario con id elimina al usuario con nombre name de su lista de amigos, devuelve una excepción en caso de fallo
    AmigoResource removeFriend(Integer id, String name);

    //Devuelve la lista de amigos
    List<String> getFriends(Integer id);

    //Devuelve una lista con publicaciones del amigo de id con nombre name, devuelve null si no existe usuario con nombre name y excepción si existe pero no es su am
    List<PreviewPublicacion> viewPostOfFriend(Integer id, String name);

}
