package main.persistence.entity;

import main.persistence.IDs.IDvaloracion;

import javax.persistence.*;

@Entity
@IdClass(IDvaloracion.class)
public class Valoracion {

  @Id
  private Integer idpubli;
  @Id
  private Integer iduser;
  private Integer punt;

    public Valoracion(Integer idPubli, Integer idUser, Integer Punt) {
        idpubli = idPubli;
        iduser = idUser;
        punt = Punt;
    }

    protected Valoracion(){}

    public Integer getIdPubli() {
        return idpubli;
    }

    public Integer getIdUser() {
        return iduser;
    }

    public Integer getPunt() {
        return punt;
    }
}
