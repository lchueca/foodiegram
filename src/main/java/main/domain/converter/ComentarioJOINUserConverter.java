package main.domain.converter;

import main.domain.resource.ComentarioJOINUserResource;
import main.domain.resource.ComentarioResource;
import main.persistence.entity.ComentarioJOINUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ComentarioJOINUserConverter implements Converter<ComentarioJOINUser, ComentarioJOINUserResource> {

    @Override
    public ComentarioJOINUserResource convert(ComentarioJOINUser source){

        if (source == null)
            return null;

        ComentarioJOINUserResource response=new ComentarioJOINUserResource();
        response.setId(source.getId());
        response.setIdpubli(source.getIdpubli());
        response.setIduser(source.getIduser());
        response.setText(source.getText());
        response.setUser(source.getName());
        response.setPfp(source.getImage());
        return response;

    }

}