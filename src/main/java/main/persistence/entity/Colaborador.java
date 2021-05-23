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
    private String localization;
    private Boolean vip;
    private Integer money;

    public Colaborador(Integer id,String origin, String type, String localization) {
        this.id = id;
        this.origin = origin;
        this.type = type;
        this.localization = localization;
        vip=false;
        money=0;

    }



}
