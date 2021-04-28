package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String passwd;
    private String  email;
    private String image;

    public Usuario(String name, String passwd, String image, String email) {
        this.name = name;
        this.passwd = passwd;
        this.image = image;
        this.email=email;
    }

    protected Usuario() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
