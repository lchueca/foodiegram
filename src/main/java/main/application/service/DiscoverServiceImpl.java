package main.application.service;


import main.domain.converter.PreviewPublicacionConverter;
import main.domain.converter.PublicacionConverter;
import main.domain.converter.UsuarioConverter;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscoverServiceImpl implements DiscoverService {

    @Autowired
    private RepoPublicacion repoPublicacion;

    @Autowired
    private RepoUsuario repoUsuario;

    private PublicacionConverter publicacionConverter= new PublicacionConverter();
    private UsuarioConverter usuarioConverter = new UsuarioConverter();
    private PreviewPublicacionConverter previewConverter = new PreviewPublicacionConverter();


    public List<PreviewPublicacion> discoverByAmigo(){

        Integer userid=Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Publicacion> publi=repoPublicacion.findbyFriend(userid);

        if(publi==null)
            return  null;

        return publi.stream().map(previewConverter::convert).collect(Collectors.toList());

    }


    public List<PreviewPublicacion> discoverBestRated(String period) throws IllegalArgumentException{

        Integer quantity = 1;

        if (!period.equals("month") && !period.equals("day") && !period.equals("week") && !period.equals("year") && !period.equals("allTime"))
            throw new IllegalArgumentException("Invalid period string.");

        if (period.equals("allTime")) {
            quantity = 99;
            period = "year";
        }

        List<Publicacion> publi = repoPublicacion.discoverBestRated(quantity, period);

        if(publi==null)
            return  null;

        return publi.stream().map(previewConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<PreviewPublicacion> discoverMostRated(String period) {
        Integer quantity = 1;

        if (!period.equals("month") && !period.equals("day") && !period.equals("week") && !period.equals("year") && !period.equals("allTime"))
            throw new IllegalArgumentException();

        if (period.equals("allTime")) {
            quantity = 99;
            period = "year";
        }

        List<Publicacion> publi = repoPublicacion.discoverMostRated(quantity, period);

        if(publi==null)
            return  null;

        return publi.stream().map(previewConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResource> findFollowedByFriends(Integer userid) {
        List<Usuario> list = repoUsuario.findFollowedByFriends(userid);

        return list.stream().map(usuarioConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<PreviewPublicacion> popularSameCity(Double lat, Double lon) {
        return null;
    }


}
