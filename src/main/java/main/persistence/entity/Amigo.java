package main.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.persistence.IDs.IDamigo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@IdClass(IDamigo.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="amigo")
public class Amigo {

    @Id
    private Integer iduser1;
    @Id
    private Integer iduser2;

}
