package main.persistence.IDs;

import java.io.Serializable;
import java.util.Objects;

public class IDamigo implements Serializable {

    private Integer iduser1;
    private Integer iduser2;

    public IDamigo(){

    }

    public IDamigo(Integer id1, Integer id2){
        this.iduser1 = id1;
        this.iduser2 = id2;

    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        IDamigo aux = (IDamigo) o;
        return this.iduser1.equals(aux.iduser1) && this.iduser2.equals(aux.iduser2);
    }

    @Override
    public int hashCode(){return Objects.hash(iduser1, iduser2);}

}
