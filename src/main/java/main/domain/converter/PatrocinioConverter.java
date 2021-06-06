package main.domain.converter;


import main.domain.resource.PatrocinioResource;
import main.persistence.entity.Patrocinio;
import org.springframework.core.convert.converter.Converter;

public class PatrocinioConverter implements Converter<Patrocinio, PatrocinioResource> {

    @Override
    public PatrocinioResource convert(Patrocinio source) {
        return source != null ? new PatrocinioResource(source.getId(), source.getEndtime()) : null;
    }
}
