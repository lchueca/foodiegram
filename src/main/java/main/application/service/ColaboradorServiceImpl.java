package main.application.service;


import main.domain.converter.ColaboradorConverter;
import main.domain.resource.ColaboradorResource;
import main.persistence.entity.Colaborador;
import main.persistence.entity.RoleEnum;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoColaborador;
import main.persistence.repository.RepoUsuario;
import main.rest.forms.CollaborateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    private ColaboradorConverter converterCol = new ColaboradorConverter();

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoColaborador repoColaborador;

    @Autowired
    private RestService restService;

    @Override
    public ColaboradorResource upgradeUser(Integer userid, CollaborateForm form) {

        String country=null;
        String city=null;
        String street= null;


        Usuario user = repoUsuario.findOne(userid);

        if (form.getLatitud() != null && form.getLongitud() != null) {
            Map<String, Object> geoData = getCity(form.getLatitud() , form.getLongitud());
            try {
                country = geoData.get("country").toString();
                city = geoData.get("locality").toString();
                street=geoData.get("street").toString();
            }

            catch (NullPointerException ignored) {

            }
        }

        Colaborador colaborador=new Colaborador(user.getId(), form.getOrigin(), form.getType(), country,city,street);
        user.setRole(RoleEnum.ROLE_COL);
        repoUsuario.save(user);
        repoColaborador.save(colaborador);

        return converterCol.convert(colaborador);
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
