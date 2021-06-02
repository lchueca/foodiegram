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
public class ComentarioJOINUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer idpubli;
    private Integer iduser;
    private String text;
    private String name;
    private String image;

    public ComentarioJOINUser(Integer idPubli, Integer idUser, String text) {
        this.idpubli = idPubli;
        iduser = idUser;
        this.text = text;
    }
}
