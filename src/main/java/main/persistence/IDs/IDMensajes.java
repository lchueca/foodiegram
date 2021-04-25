package main.persistence.IDs;
import java.util.Objects;
import java.io.Serializable;

public class IDMensajes implements Serializable {
    private Integer id;
    private Integer idUser1;
    private Integer idUser2;

    public IDMensajes(){

    }

    public IDMensajes(Integer Id,Integer IdUser1,Integer IdUser2){
        this.id=Id;
        this.idUser1=IdUser1;
        this.idUser2=IdUser2;
    }
    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || getClass() != o.getClass())return false;
        IDMensajes aux= (IDMensajes) o;
        return this.id.equals(aux.id) && this.idUser1.equals(aux.idUser1) && this.idUser2.equals(aux.idUser2);

    }

    @Override
    public int hashCode(){
        return Objects.hash(id,idUser1,idUser2);
    }



}
