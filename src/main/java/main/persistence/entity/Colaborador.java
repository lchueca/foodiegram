package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Colaborador {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;
    private String Origin;
    private String Type;
    private String Localization;
    private Boolean Vip;
    private Integer Money;

    public Colaborador(String origin, String type, String localization, Boolean vip, Integer money) {
        Origin = origin;
        Type = type;
        Localization = localization;
        Vip = vip;
        Money = money;
    }

    protected Colaborador() {}


    public Integer getId() {
        return Id;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getType() {
        return Type;
    }

    public String getLocalization() {
        return Localization;
    }

    public Boolean getVip() {
        return Vip;
    }

    public Integer getMoney() {
        return Money;
    }
}
