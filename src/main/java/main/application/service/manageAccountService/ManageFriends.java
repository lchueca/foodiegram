package main.application.service.manageAccountService;


import main.domain.resource.AmigoResource;
import main.domain.resource.PublicacionResource;

import java.util.List;

public interface ManageFriends {

    AmigoResource addFriend(Integer id, String name);
    AmigoResource removeFriend(Integer id, String name);
    List<PublicacionResource> viewPostOfFriend(Integer id, String name);

}
