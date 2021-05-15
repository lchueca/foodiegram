package main.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.annotation.Resource;

@Resource
@Data
@AllArgsConstructor
public class PreviewUsuario {

    private String name;
    private String image;
}
