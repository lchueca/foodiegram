package main.domain.resource;

import lombok.Data;

import javax.annotation.Resource;

@Resource
@Data
public class ComentarioResource {

    private Integer id;
    private Integer idpubli;
    private Integer iduser;
    private String text;

}
