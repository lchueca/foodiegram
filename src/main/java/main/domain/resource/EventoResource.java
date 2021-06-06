package main.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Resource;

@Resource
@Data
@AllArgsConstructor
public class EventoResource {

    private Integer id;
    private Integer idcolab;
    private String text;
    private String image;
    private java.sql.Date date;
}
