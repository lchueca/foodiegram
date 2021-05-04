package main.domain.resource;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Resource;

@Resource
@Data
public class AmigoResource {

    private Integer iduser1;
    private Integer iduser2;

}
