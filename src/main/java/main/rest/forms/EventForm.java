package main.rest.forms;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventForm {

    private  Integer idCollab;
    private  String text;
    private MultipartFile image;
    private Date date;


}
