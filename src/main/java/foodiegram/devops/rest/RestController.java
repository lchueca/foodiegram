package foodiegram.devops.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/images")
public class RestController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadPhoto(@RequestHeader(value="Content-type") String header, @RequestPart("user") String user, @RequestPart("image1") MultipartFile image) {
        System.out.println("asdasd");
        String type = image.getContentType().split("/")[0];
        String format = image.getContentType().split("/")[1];

        if (type.equals("image")) {

            File folder = new File(System.getProperty("user.dir") +"/images/" + user);
            folder.mkdirs();

            try (FileOutputStream stream = new FileOutputStream(folder.getAbsolutePath() + "/" + image.getOriginalFilename() + "." + format)) {
                stream.write(image.getBytes());
            }

            catch(Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            return ResponseEntity.status(HttpStatus.OK).body(null);

        }

        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("That's not an image.");

    }

    @RequestMapping(value="/saludo", method = RequestMethod.GET)
    public String saluda() {
        System.out.println("hgola");
        return "Hola";
    }
}