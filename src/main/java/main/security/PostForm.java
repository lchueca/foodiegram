package main.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@AllArgsConstructor
public class PostForm {

    private MultipartFile image;
    private String text;
    private String latitud;
    private String longitud;


    public PostForm(){

    }
}
