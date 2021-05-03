package main.persistence.entity;

import main.persistence.IDs.IDvaloracion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(IDvaloracion.class)
public class Valoracion {

  @Id
  private Integer idpubli;
  @Id
  private Integer iduser;
  private Float punt;

    public Valoracion(Integer idPubli, Integer idUser, Float Punt) {
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

    public Float getPunt() {
        return punt;
    }
}
