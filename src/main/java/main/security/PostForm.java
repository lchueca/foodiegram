package main.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class PostForm {

    private File image;
    private String text;

    public PostForm(){

    }
}
