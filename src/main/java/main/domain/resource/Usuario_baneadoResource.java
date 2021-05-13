package main.domain.resource;

import lombok.Data;

import javax.annotation.Resource;
import java.util.Date;
@Resource
@Data
public class Usuario_baneadoResource {
    private int id;
    private Date date;
}
