package main.application.service.manageAccountService;

import main.application.service.UserServiceImpl;
import main.domain.converter.PublicacionConverter;
import main.domain.resource.PreviewPublicacion;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewImagesImpl implements  ViewImages{

    private final PublicacionConverter postConverter = new PublicacionConverter();

    @Autowired
    RepoPublicacion repoPost;

    @Autowired
    RepoUsuario repoUser;

    @Override
    public List<PreviewPublicacion> viewPost(Integer idUser) {

        Usuario user = repoUser.findById(idUser);

        if(user == null) //comprobamos que existe el usuario con idUser
            return null;
        else{
            UserServiceImpl userService = new UserServiceImpl();
            return userService.getPosts(idUser);

        }
    }
}
