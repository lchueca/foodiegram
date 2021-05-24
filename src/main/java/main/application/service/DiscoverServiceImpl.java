package main.application.service;

import main.domain.converter.PublicacionConverter;
import main.domain.converter.UsuarioConverter;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscoverServiceImpl  implements DiscoverService {

    @Autowired
    private RepoPublicacion repoPublicacion;

    @Autowired
    private RepoUsuario repoUsuario;

    private PublicacionConverter publicacionConverter= new PublicacionConverter();
    private UsuarioConverter usuarioConverter = new UsuarioConverter();


    public List<PublicacionResource> discoverByAmigo(Integer userid){

        List<Publicacion> publi=repoPublicacion.findbyFriend(userid);

        if(publi==null)
            return  null;

        return publi.stream().map(publicacionConverter::convert).collect(Collectors.toList());

    }


    public List<PublicacionResource> discoverByPopularity(){

        List<Publicacion> publi=repoPublicacion.findByPopularity();

        if(publi==null)
            return  null;

        return publi.stream().map(publicacionConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResource> findFollowedByFriends(Integer userid) {
        List<Usuario> list = repoUsuario.findFollowedByFriends(userid);

        return list.stream().map(usuarioConverter::convert).collect(Collectors.toList());
    }

}
