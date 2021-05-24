package main.application.service;

import com.google.gson.Gson;
import main.domain.converter.ComentarioConverter;
import main.domain.converter.PublicacionConverter;
import main.domain.converter.ValoracionConverter;
import main.domain.resource.ComentarioResource;
import main.domain.resource.PublicacionResource;
import main.domain.resource.ValoracionResource;
import main.persistence.IDs.IDvaloracion;
import main.persistence.entity.Comentario;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.entity.Valoracion;
import main.persistence.repository.RepoComentario;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import main.persistence.repository.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NoPermissionException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicacionConverter converterPubli = new PublicacionConverter();

    private final ValoracionConverter converterVal = new ValoracionConverter();

    private final ComentarioConverter converterCom = new ComentarioConverter();

    private final Pattern imagePattern = Pattern.compile("\\w+.(png|jpg)$");

    @Autowired
    private RepoPublicacion repoPubli;

    @Autowired
    private RepoValoracion repoVal;

    @Autowired
    private RepoComentario repoComen;

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RestService restService;

    @Value("${apache.address}")
    private String apacheAddress;

    @Value("${apache.rootFolder}")
    private String apacheRootFolder;

    @Override
    public PublicacionResource getPost(Integer pubID) {
        return converterPubli.convert(repoPubli.findOne(pubID));
    }

    @Override
    public PublicacionResource editPost(Integer pubID, String text) throws IllegalArgumentException, NoPermissionException {

        if (text == null)
            throw new IllegalArgumentException("Text or loc should be not null");


        Publicacion publi = repoPubli.findOne(pubID);

        if (publi != null) {

            publi.setText(text);


            repoPubli.save(publi);

        }

        return converterPubli.convert(publi);

    }

    @Override
    public PublicacionResource deletePost(Integer pubID) throws NoPermissionException {

        Publicacion publi = repoPubli.findOne(pubID);

        if (publi != null)
            repoPubli.delete(publi);


        return converterPubli.convert(publi);


    }

    @Override
    public PublicacionResource upload(Integer userID, String text, MultipartFile image, Double lat, Double lon) throws IOException, IllegalArgumentException {
        String country = null;
        String city = null;

        if (lat != null && lon != null) {
            Map<String, Object> geoData = getCity(lat, lon);
            try {
                country = geoData.get("country").toString();
                city = geoData.get("region").toString();
            }

            catch (NullPointerException ignored) {

            }
        }


        Matcher matcher = imagePattern.matcher(image.getOriginalFilename());

        if (!matcher.matches())
            throw new IllegalArgumentException("Only jpeg and png images are supported.");

        // Se crea una publicacion sin imagen
        Publicacion publi = new Publicacion(text, userID, country, city);
        publi = repoPubli.save(publi);

        try {
            File folder = new File(apacheRootFolder + "/users/" + userID);
            folder.mkdirs();

            String name = folder.getAbsolutePath() + "/" + publi.getId() + "." + matcher.group(1);
            FileOutputStream stream = new FileOutputStream(name);
            stream.write(image.getBytes());
            stream.close();

            // Si se ha conseguido guardar la imagen, se le asocia a la publicacion una direccion en la BD.
            String address = String.format("%s/users/%s/%s.%s", apacheAddress, userID, publi.getId(), matcher.group(1));
            publi.setImage(address);
            repoPubli.save(publi);
        } catch (IOException e) {
            repoPubli.delete(publi);
            throw e;
        }

        return converterPubli.convert(publi);
    }

    @Override
    public List<ValoracionResource> getRatings(Integer pubID) {

        Publicacion publi = repoPubli.findOne(pubID);

        if (publi == null)
            return null;

        else {
            List<Valoracion> valoraciones = repoVal.findByIdpubli(pubID);
            return valoraciones.stream().map(converterVal::convert).collect(Collectors.toList());
        }
    }

    @Override
    public ValoracionResource setRating(Integer pubID, Integer userID, Float score) throws IllegalArgumentException {

        Usuario usuario = repoUsuario.findOne(userID);


        if (score < 0 || score > 5)
            throw new IllegalArgumentException("Punt must be a integer between 0 and 5");

        else {
            Valoracion valora = new Valoracion(pubID, userID, score);
            repoVal.save(valora);

            return converterVal.convert(valora);
        }

    }

    @Override
    public ValoracionResource getRating(Integer pubID, String user) {

        Usuario usuario = repoUsuario.findByName(user);

        if (usuario == null)
            return null;

        return converterVal.convert(repoVal.findOne(new IDvaloracion(pubID, usuario.getId())));

    }

    @Override
    public ValoracionResource deleteRating(Integer pubID, Integer userid) {

        Usuario usuario = repoUsuario.findOne(userid);

        Valoracion valor = repoVal.findOne(new IDvaloracion(pubID, userid));

        if (valor != null)
            repoVal.delete(valor);

        return converterVal.convert(valor);
    }

    @Override
    public List<ComentarioResource> getComments(Integer pubID) {

        Publicacion pub = repoPubli.findOne(pubID);

        if (pub == null)
            return null;

        else {

            List<Comentario> comentarios = repoComen.findByIdpubli(pubID);
            return comentarios.stream().map(converterCom::convert).collect(Collectors.toList());
        }

    }

    @Override
    public ComentarioResource setComment(Integer pubID, Integer userID, String text) throws IllegalArgumentException {

        if (text == null || text.length() == 0)
            throw new IllegalArgumentException("Text must be not null");


        Comentario comment = new Comentario(pubID, userID, text);
        repoComen.save(comment);
        return converterCom.convert(comment);

    }

    private Map<String, Object> getCity(Double lat, Double lon) {
        String latitude = lat.toString().replace(",", ".");
        String longitude = lon.toString().replace(",", ".");

        String query = String.format(
                "http://api.positionstack.com/v1/reverse?access_key=%s&query=%s,%s&limit=1",
                "7d674e3c9eafdf7c3e6027f5a39fe866", latitude, longitude);

        List<Map<String, Object>> data = (List<Map<String, Object>>) restService.getJSON(query).get("data");
        return data.get(0);
    }
}

