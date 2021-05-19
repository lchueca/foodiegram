package main.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.persistence.IDs.IDamigo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(IDamigo.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amigo {

    @Id
    private Integer iduser1;
    @Id
    private Integer iduser2;

}
