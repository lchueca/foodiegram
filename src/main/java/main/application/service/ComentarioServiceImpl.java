package main.application.service;

import main.domain.converter.ComentarioConverter;
import main.domain.resource.ComentarioResource;
import main.persistence.entity.Comentario;
import main.persistence.repository.RepoComentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    RepoComentario repoComen;

    private ComentarioConverter comentarioConverter = new ComentarioConverter();


    @Override
    public ComentarioResource editComentario(Integer comID,String text) throws IllegalArgumentException {


        Comentario comment = repoComen.findOne(comID);

        if (comment == null)
            return null;

        else {
            if (text != null)
                comment.setText(text);
            else
                throw new IllegalArgumentException();
        }

            repoComen.save(comment);

        return  comentarioConverter.convert(comment);
    }

    @Override
    public ComentarioResource deleteComentario(Integer comID) {

        Comentario comment = repoComen.findOne(comID);

        if (comment != null)
            repoComen.delete(comID);

        return comentarioConverter.convert(comment);


    }
}
