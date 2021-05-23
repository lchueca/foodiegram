package main.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class PubliJOINUserJOINAmigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userid;
    private Integer userid2;
    private String name;
    private String userimage;
    private String text;
    private String image;
    private String ciudad;
    private String Pais;
    private Float media;
    private Integer numerototalval;


}
