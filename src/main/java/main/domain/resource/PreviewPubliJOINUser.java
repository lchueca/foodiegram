package main.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Resource;

@Resource
@Data
@AllArgsConstructor
public class PreviewPubliJOINUser {

    private String name;
    private String userimage;
    private String text;
    private String image;
    private String localization;
    private Float media;
    private Integer numerototalval;
}
