package main.domain.converter;

import main.domain.resource.ValoracionResource;
import main.persistence.entity.Valoracion;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ValoracionConverter implements Converter<Valoracion, ValoracionResource> {

    @Override
    public ValoracionResource convert(Valoracion source){

        if (source == null)
            return null;

        ValoracionResource respose= new ValoracionResource();
        respose.setPunt(source.getPunt());
        respose.setIdpubli(source.getIdpubli());
        respose.setIduser(source.getIduser());
        return respose;
    }
}
