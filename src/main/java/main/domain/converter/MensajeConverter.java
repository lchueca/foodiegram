package main.domain.converter;

import main.domain.resource.MensajeResource;
import main.persistence.entity.Mensaje;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MensajeConverter implements Converter<Mensaje, MensajeResource> {

    @Override
    public  MensajeResource convert(Mensaje source){

        if (source == null)
            return null;

        MensajeResource response = new MensajeResource();
        response.setId(source.getId());
        response.setIduser1(source.getIdUser1());
        response.setIduser2(source.getIdUser2());
        response.setText(source.getText());
        return response;
    }


}