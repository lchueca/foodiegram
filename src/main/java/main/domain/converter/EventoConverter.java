package main.domain.converter;

import main.domain.resource.EventoResource;
import main.persistence.entity.Evento;
import org.springframework.core.convert.converter.Converter;

public class EventoConverter implements Converter<Evento, EventoResource> {

    @Override
    public EventoResource convert(Evento source) {
        return source != null ? new EventoResource(source.getId(), source.getIdcolab(), source.getText(), source.getImage(), source.getEndtime()) : null;
    }
}
