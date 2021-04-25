package main.persistence.IDs;

import java.io.Serializable;
import java.util.Objects;

public class IDvaloracion implements Serializable {

    private Integer idpubli;
    private Integer iduser;

    public IDvaloracion() {

    }

    public  IDvaloracion(Integer IDpubli,Integer IDuser){

        idpubli = IDpubli;
        iduser = IDuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IDvaloracion aux = (IDvaloracion) o;
        return this.idpubli.equals(aux.idpubli ) && this.iduser.equals(aux.iduser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpubli, iduser);
    }

}
