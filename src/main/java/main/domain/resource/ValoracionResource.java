package main.domain.resource;

import lombok.Data;

import javax.annotation.Resource;

@Resource
@Data
public class ValoracionResource {

    private Integer idpubli;
    private Integer iduser;
    private Float punt;
    
}
