package main.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumValPubli {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer iduser;
    private Integer numval;
    private Integer numpubli;

}
