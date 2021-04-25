package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Colaborador {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String origin;
    private String type;
    private String localization;
    private Boolean vip;
    private Integer money;

    public Colaborador(String origin, String type, String localization, Boolean vip, Integer money) {
        this.origin = origin;
        this.type = type;
        this.localization = localization;
        this.vip = vip;
        this.money = money;
    }

    protected Colaborador() {}


    public Integer getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getType() {
        return type;
    }

    public String getLocalization() {
        return localization;
    }

    public Boolean getVip() {
        return vip;
    }

    public Integer getMoney() {
        return money;
    }
}
