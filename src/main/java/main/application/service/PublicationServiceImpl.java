package main.application.service;

import com.google.gson.Gson;
import com.sun.javaws.exceptions.InvalidArgumentException;
import lombok.AllArgsConstructor;
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
import main.persistence.repository.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
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
    public ValoracionResource setRating(Integer pubID, Integer user, Integer score) throws IllegalArgumentException, DataIntegrityViolationException {

        if(score>=0 &&score<=5) {

            Valoracion valora = new Valoracion(pubID, user, score);
            repoVal.save(valora);

            return converterVal.convert(valora);
        }

        else
            throw new IllegalArgumentException("Punt must be a integer between 0 and 5");

    }

    @Override
    public ValoracionResource getRating(Integer pubID, Integer user) {

       return converterVal.convert(repoVal.findOne(new IDvaloracion(pubID,user)));

    }

    @Override
    public ValoracionResource deleteRating(Integer pubID, Integer user) {

        Valoracion valor = repoVal.findOne(new IDvaloracion(pubID, user));
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
    public ComentarioResource setComment(Integer pubID, Integer userID, String text) throws DataIntegrityViolationException {

        Comentario comment = new Comentario(pubID,userID,text);
        repoComen.save(comment);
        return  converterCom.convert(comment);

    }
}
