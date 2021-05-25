package main.rest.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborateForm {

    private String origin;
    private String type;
    private String latitud;
    private String longitud;


    public Double getLatitud() {
        return latitud != null ?Double.parseDouble(latitud) : null;
    }

    public Double getLongitud() {
        return longitud != null ? Double.parseDouble(longitud) : null;
    }
}
