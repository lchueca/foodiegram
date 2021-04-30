package main.domain.converter;

import main.domain.resource.PublicacionResource;
import main.persistence.entity.Publicacion;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PublicacionConverter implements Converter<Publicacion, PublicacionResource> {

    @Override
    public PublicacionResource convert(Publicacion source){

        if (source == null)
            return null;

        PublicacionResource response = new PublicacionResource();
        response.setId(source.getId());
        response.setIduser(source.getIduser());
        response.setText(source.getText());
        response.setImage(source.getImage());
        response.setLocalization(source.getLocalization());
        response.setMedia(source.getMedia());
        response.setNumerototalval(source.getNumerototalval());
        return response;
    }


}
