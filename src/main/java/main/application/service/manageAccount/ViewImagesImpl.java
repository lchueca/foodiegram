package main.application.service.manageAccount;

import main.domain.converter.PublicacionConverter;
import main.domain.resource.PublicacionResource;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ViewImagesImpl implements  ViewImages{

    private final PublicacionConverter postConverter = new PublicacionConverter();

    @Autowired
    RepoPublicacion repoPost;

    @Autowired
    RepoUsuario repoUser;

    @Override
    public List<PublicacionResource> viewImages(Integer idUser) {

        Usuario user = repoUser.findById(idUser);

        if(user == null) //comprobamos que existe el usuario con idUser
            return null;
        else{
            List<Publicacion> _listPost = repoPost.findByiduser(idUser);
            return _listPost.stream().map(postConverter::convert).collect(Collectors.toList());

        }
    }
}
