package main.domain.converter;
import main.domain.resource.AmigoResource;
import main.persistence.entity.Amigo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

public class AmigoConverter implements Converter<Amigo, AmigoResource> {

    @Override
    public AmigoResource convert(Amigo amigo) {

        if(amigo == null)
            return null;

        AmigoResource response = new AmigoResource();
        response.setIduser1(amigo.getIduser1());
        response.setIduser2(amigo.getIduser2());
        return response;
    }
}
