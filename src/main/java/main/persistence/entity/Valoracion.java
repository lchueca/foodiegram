package main.persistence.entity;

import main.persistence.IDs.IDvaloracion;

import javax.persistence.*;

@Entity
@IdClass(IDvaloracion.class)
public class Valoracion {

  @Id
  private Integer id_publi;
  @Id
  private Integer id_user;
  private Integer punt;

    public Valoracion(Integer idPubli, Integer idUser, Integer Punt) {
        id_publi = idPubli;
        id_user = idUser;
        punt = Punt;
    }

    protected Valoracion(){}

    public Integer getIdPubli() {
        return id_publi;
    }

    public Integer getIdUser() {
        return id_user;
    }

    public Integer getPunt() {
        return punt;
    }
}
