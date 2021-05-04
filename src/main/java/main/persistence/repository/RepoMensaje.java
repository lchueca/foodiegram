package main.persistence.repository;

import main.persistence.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepoMensaje extends JpaRepository<Mensaje,Integer>  {
    public List<Mensaje> findByiduser1(Integer iduser1);


}
