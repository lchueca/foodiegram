package main.domain.resource;

import lombok.Data;

import javax.annotation.Resource;

@Resource
@Data
public class UsuarioResource {

    private Integer id;
    private String name;
    private String email;
    private String image;

}
