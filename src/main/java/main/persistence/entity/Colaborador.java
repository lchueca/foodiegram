package main.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Colaborador {
    @Id
    private Integer id;
    private String origin;
    private String type;
    private String pais;
    private String ciudad;
    private String calle;
    private Boolean vip;
    private Float money;

    public Colaborador(Integer id,String origin, String type, String pais,String ciudad,String calle) {
        this.id = id;
        this.origin = origin;
        this.type = type;
        this.calle=calle;
        this.ciudad=ciudad;
        this.pais=pais;
        vip=false;
        money=0.0f;

    }



}
