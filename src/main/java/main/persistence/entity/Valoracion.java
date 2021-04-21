package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Valoracion {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer Id;
  private Integer IdPubli;
  private Integer IdUser;
  private Integer Punt;

    public Valoracion(Integer idPubli, Integer idUser, Integer punt) {
        IdPubli = idPubli;
        IdUser = idUser;
        Punt = punt;
    }

    protected Valoracion(){}

    public Integer getIdPubli() {
        return IdPubli;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getIdUser() {
        return IdUser;
    }

    public Integer getPunt() {
        return Punt;
    }
}
