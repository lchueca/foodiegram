package main.persistence.IDs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class IDmeetUp implements Serializable {

    private Integer id;
    private Integer iduser;

    protected IDmeetUp(){}
}
