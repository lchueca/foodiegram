package main.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.persistence.IDs.IDvaloracion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(IDvaloracion.class)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Valoracion {

  @Id
  private Integer idpubli;
  @Id
  private Integer iduser;

  private Float punt;

}
