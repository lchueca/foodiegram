package main.application.service;

import main.domain.converter.ComentarioConverter;
import main.domain.converter.PublicacionConverter;
import main.domain.converter.ValoracionConverter;
import main.domain.resource.ComentarioResource;
import main.domain.resource.PublicacionResource;
import main.domain.resource.ValoracionResource;
import main.persistence.IDs.IDvaloracion;
import main.persistence.entity.Comentario;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.entity.Valoracion;
import main.persistence.repository.RepoComentario;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import main.persistence.repository.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@Component
public class PublicationServiceImpl implements PublicationService {

    private final PublicacionConverter converterPubli = new PublicacionConverter();

    private final ValoracionConverter converterVal = new ValoracionConverter();

    private final ComentarioConverter converterCom = new ComentarioConverter();

    @Autowired
    RepoPublicacion repoPubli;

    @Autowired
    private RepoValoracion repoVal;

    @Autowired
    private RepoComentario repoComen;

    @Autowired
    private RepoUsuario repoUsuario;

    @Value("${apache.address}")
    private String apacheAddress;

    @Value("${apache.rootFolder}")
    private String apacheRootFolder;



    @Override
    public PublicacionResource getPost(Integer pubID) {
        return converterPubli.convert(repoPubli.findOne(pubID));
    }

    @Override
    public PublicacionResource editPost(Integer pubID, String text, String loc) throws IllegalArgumentException {

        if (text == null && loc == null)
            throw new IllegalArgumentException("Text or loc should be not null");


        Publicacion publi = repoPubli.findOne(pubID);

         if (publi != null) {

            if (text != null)
                publi.setText(text);

            if (loc != null)
                publi.setLocalization(loc);

            repoPubli.save(publi);
        }

        return converterPubli.convert(publi);

    }

    @Override
    public PublicacionResource deletePost(Integer pubID) {

        Publicacion publi = repoPubli.findOne(pubID);

        if (publi != null)
            repoPubli.delete(publi);

        return converterPubli.convert(publi);


    }

    @Override
    public List<ValoracionResource> getRatings(Integer pubID) {

        Publicacion publi = repoPubli.findOne(pubID);

        if (publi == null)
            return null;

        else {
            List<Valoracion> valoraciones = repoVal.findByidpubli(pubID);
            return valoraciones.stream().map(converterVal::convert).collect(Collectors.toList());
        }
    }

    @Override
    public ValoracionResource setRating(Integer pubID, String user, Float score) throws IllegalArgumentException {

        Usuario usuario = repoUsuario.findByName(user);

        if (usuario == null)
            throw new IllegalArgumentException("That user does not exist.");

        if(score<0 || score>5)
            throw new IllegalArgumentException("Punt must be a integer between 0 and 5");

        else {
            Valoracion valora = new Valoracion(pubID, usuario.getId(), score);
            repoVal.save(valora);

            return converterVal.convert(valora);
        }

    }

    @Override
    public ValoracionResource getRating(Integer pubID, String user)  {

        Usuario usuario = repoUsuario.findByName(user);

        if (usuario == null)
            return  null;

       return converterVal.convert(repoVal.findOne(new IDvaloracion(pubID,usuario.getId())));

    }

    @Override
    public ValoracionResource deleteRating(Integer pubID, String user)  {

        Usuario usuario = repoUsuario.findByName(user);

        if (usuario == null)
            return null;

        Valoracion valor = repoVal.findOne(new IDvaloracion(pubID, usuario.getId()));

        if (valor != null)
            repoVal.delete(valor);

        return converterVal.convert(valor);
    }

    @Override
    public List<ComentarioResource> getComments(Integer pubID) {

        Publicacion pub = repoPubli.findOne(pubID);

        if (pub == null)
            return null;

        else {

            List<Comentario> comentarios = repoComen.findByidpubli(pubID);
            return comentarios.stream().map(converterCom::convert).collect(Collectors.toList());
        }

    }

    @Override
    public ComentarioResource setComment(Integer pubID, String userID, String text) throws IllegalArgumentException {

        if (text == null || text.length() == 0)
            throw new IllegalArgumentException("Text must be not null");

        Usuario usuario = repoUsuario.findByName(userID);

        if (usuario == null)
            throw new IllegalArgumentException("That user does not exist.");

        Comentario comment = new Comentario(pubID, usuario.getId(), text);
        repoComen.save(comment);
        return converterCom.convert(comment);

    }
}
