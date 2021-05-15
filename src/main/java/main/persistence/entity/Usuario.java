package main.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String passwd;
    private String email;
    private String image;
    private boolean enabled;


    public Usuario(String name, String passwd, String image, String email) {
        this.name = name;
        this.passwd = passwd;
        this.image = image;
        this.email=email;
        this.enabled=false;

    }

    protected Usuario() {}

}
