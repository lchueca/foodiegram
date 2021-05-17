package main.persistence.IDs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
public class IDvaloracion implements Serializable {

    private Integer idpubli;
    private Integer iduser;

    protected IDvaloracion() {}

}
