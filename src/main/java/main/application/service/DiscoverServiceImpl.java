package main.application.service;

import main.domain.converter.PublicacionConverter;
import main.domain.resource.PublicacionResource;
import main.persistence.entity.Publicacion;
import main.persistence.repository.RepoPublicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscoverServiceImpl  implements DiscoverService{

    @Autowired
    private RepoPublicacion repoPublicacion;

    private PublicacionConverter publicacionConverter= new PublicacionConverter();


    public List<PublicacionResource> discoverByAmigo(Integer userid){

        List<Publicacion> publi=repoPublicacion.findbyFriend(userid);

        if(publi==null)
            return  null;

        return publi.stream().map(publicacionConverter::convert).collect(Collectors.toList());

    }

}
