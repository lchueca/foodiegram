package main.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String passwd;
    private String email;
    private String image;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;


    public Usuario(String name, String passwd, String image, String email) {
        this.name = name;
        this.passwd = passwd;
        this.image = image;
        this.email=email;
        this.enabled=false;
        this.role = null;

    }

}
