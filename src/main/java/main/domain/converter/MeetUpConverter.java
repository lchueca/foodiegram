package main.domain.converter;

import main.domain.resource.MeetupResource;
import main.persistence.entity.MeetUp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MeetUpConverter implements Converter<MeetUp, MeetupResource> {


    @Override
    public MeetupResource convert(MeetUp source) {


        if(source==null)
            return  null;

        MeetupResource response= new MeetupResource();

        response.setEventId(source.getId());
        response.setEventId(source.getIduser());
        return response;
    }
}
