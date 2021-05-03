package main.domain.resource;

import lombok.Data;

import javax.annotation.Resource;

@Resource
@Data
public class PublicacionResource {

    private Integer id;
    private Integer iduser;
    private String text;
    private String image;
    private String localization;
    private String media;
    private Integer numerototalval;

}
