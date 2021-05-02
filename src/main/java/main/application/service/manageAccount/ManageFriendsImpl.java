package main.application.service.manageAccount;

import main.domain.converter.AmigoConverter;
import main.domain.resource.AmigoResource;
import main.domain.resource.PublicacionResource;
import main.persistence.entity.Amigo;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoAmigo;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ManageFriendsImpl implements ManageFriends{


    private final AmigoConverter friendConverter = new AmigoConverter();

    @Autowired
    RepoUsuario repoUser;

    @Autowired
    RepoAmigo repoAmigo;

    @Override
    public AmigoResource addFriend(Integer id, String name) {
        Usuario user = repoUser.findByName(name);

        if(user == null) //comprobamos que el usuario existe
            return null;
        else{
            Amigo friend = new Amigo(id, user.getId());
            repoAmigo.save(friend);
            return friendConverter.convert(friend);
        }
    }

    @Override
    public AmigoResource removeFriend(Integer id, String name) {
        Usuario user = repoUser.findByName(name);

        if(user == null)//comprobamos que el usuario existe
            return null;
        else{
            Amigo friend = new Amigo(id, user.getId());
            repoAmigo.delete(friend);
            return friendConverter.convert(friend);
        }
    }

    @Override
    public List<PublicacionResource> viewPostOfFriend(Integer id, String name) {
        Usuario user = repoUser.findByName(name);

        if(user == null) //comprobamos que el usuario existe
            return null;
        else{
            Amigo friend = repoAmigo.findByIds(id, user.getId());

            if(friend == null)//comprobamos que son amigos, sino, no podrá ver sus fotos
                return null;
            else{
                ViewImagesImpl viewImagesofFriend = new ViewImagesImpl();
                return viewImagesofFriend.viewImages(user.getId());
            }
        }
    }
}
