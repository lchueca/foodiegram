package main.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Resource;
import java.sql.Date;

@Resource
@Data
@AllArgsConstructor
public class PreviewPubliJOINUser {

    private String name;
    private String userimage;
    private String text;
    private String image;
    private String pais;
    private String ciudad;
    private Date fecha;
    private Float media;
    private Integer numerototalval;
}
