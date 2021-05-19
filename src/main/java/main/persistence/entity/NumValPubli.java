package main.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
