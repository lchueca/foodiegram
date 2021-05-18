package main.persistence.entity;

        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import java.util.Date;
        @Entity
public class Usuario_baneado {
    @Id

    private int id;
    private Date fecha;
            protected Usuario_baneado() {}
    public Usuario_baneado(int id,Date date){
        this.id=id;
        this.fecha=date;
    }

    public Date getDate(){

        return fecha;
    }
    public Integer getId(){

        return id;
    }


}
