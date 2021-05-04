package main.application.service.manageAccountService;


import main.domain.resource.AmigoResource;
import main.domain.resource.PreviewPublicacion;

import java.util.List;

public interface ManageFriends {

    AmigoResource addFriend(Integer id, String name);
    AmigoResource removeFriend(Integer id, String name);
    List<PreviewPublicacion> viewPostOfFriend(Integer id, String name);

}
