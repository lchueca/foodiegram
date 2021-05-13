package main.persistence.IDs;

import lombok.AllArgsConstructor;
import lombok.Data;
import main.persistence.entity.RoleEnum;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class IDrole implements Serializable {

    Integer iduser;
    RoleEnum role;

    protected IDrole() {}

}
