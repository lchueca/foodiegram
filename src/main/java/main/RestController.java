
package main;


import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/images")
public class RestController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadPhoto(@RequestHeader(value="Content-type") String header, @RequestPart("user") String user, @RequestPart("image1") MultipartFile image) {

        String type = image.getContentType().split("/")[0];
        String format = image.getContentType().split("/")[1];

        if (type.equals("image") && (format.equals("jpg") || format.equals("png"))) {

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format");

    }

    @RequestMapping(value="/saludo", method = RequestMethod.GET)
    public String saluda() {
        return "Hola";
    }

    @RequestMapping(value="/download/{user}/{image:.+}", method = RequestMethod.GET)
    public ResponseEntity<?> downloadPhoto(@PathVariable String user, @PathVariable String image) {


        File file = new File(System.getProperty("user.dir") +"/images/" + user + "/" + image);
        MediaType type = null;

        try {

            if (image.endsWith(".jpg"))
                type = MediaType.IMAGE_JPEG;
            else if (image.endsWith(".png"))
                type = MediaType.IMAGE_PNG;
            else throw new InvalidFileNameException(null, null);

            return ResponseEntity.ok().contentType(type).body(Files.readAllBytes(file.toPath()));
        }

        catch (InvalidFileNameException e) {

            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Invalid format");
        }

        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }



    }

}