package main.domain.converter;

import main.domain.resource.ColaboradorResource;
import main.persistence.entity.Colaborador;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ColaboradorConverter implements Converter<Colaborador,ColaboradorResource> {

    @Override
    public ColaboradorResource convert(Colaborador source) {


        if(source==null)
            return  null;

        ColaboradorResource response= new ColaboradorResource();
        response.setIdUser(source.getId());
        response.setOrigin(source.getOrigin());
        response.setType(source.getType());
        response.setLocaliztion(source.getLocalization());
        response.setVip(source.getVip());
        response.setMoney(source.getMoney());

        return  response;

    }


}
