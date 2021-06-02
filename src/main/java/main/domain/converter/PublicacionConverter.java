package main.domain.converter;

import main.domain.resource.PublicacionResource;
import main.persistence.entity.Publicacion;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class PublicacionConverter implements Converter<Publicacion, PublicacionResource> {

    @Override
    public PublicacionResource convert(Publicacion source){

        DecimalFormat df = new DecimalFormat("#.##");
        if (source == null)
            return null;

        PublicacionResource response = new PublicacionResource();
        response.setId(source.getId());
        response.setIduser(source.getIduser());
        response.setText(source.getText());
        response.setImage(source.getImage());
        response.setCiudad(source.getCiudad());
        response.setPais(source.getPais());
        response.setFecha(source.getFecha());
        response.setMedia(df.format(source.getMedia()));
        response.setNumerototalval(source.getNumerototalval());
        return response;
    }


}
