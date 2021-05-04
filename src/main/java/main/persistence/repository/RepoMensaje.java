package main.persistence.repository;

import main.persistence.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Repository
public interface RepoMensaje extends JpaRepository<Mensaje,Integer>  {
    public List<Mensaje> findByiduser1(Integer iduser1);


}
