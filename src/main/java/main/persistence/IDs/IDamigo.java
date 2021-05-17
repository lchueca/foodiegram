package main.persistence.IDs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
public class IDamigo implements Serializable {

    private Integer iduser1;
    private Integer iduser2;

    protected IDamigo(){}



}
