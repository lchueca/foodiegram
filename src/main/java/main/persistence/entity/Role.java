package main.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.persistence.IDs.IDamigo;
import main.persistence.IDs.IDrole;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(IDrole.class)
public class Role {


    @Id
    private Integer iduser;

    @Id
    @Enumerated(EnumType.STRING)
    private RoleEnum role;



}
