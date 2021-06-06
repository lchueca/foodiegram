package main.domain.resource;

import lombok.Data;

import javax.annotation.Resource;

@Data
@Resource
public class MeetupResource {

    private Integer eventId;
    private Integer iduser;
}
