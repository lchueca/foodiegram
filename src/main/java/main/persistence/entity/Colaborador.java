package main.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Colaborador {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String origin;
    private String type;
    private String localization;
    private Boolean vip;
    private Float money;

    public Colaborador(String origin, String type, String localization, Boolean vip, Float money) {
        this.origin = origin;
        this.type = type;
        this.localization = localization;
        this.vip = vip;
        this.money = money;
    }


}
