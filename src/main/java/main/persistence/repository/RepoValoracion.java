package main.persistence.repository;

import main.persistence.IDs.IDvaloracion;
import main.persistence.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoValoracion extends JpaRepository<Valoracion, IDvaloracion>{

   public List<Valoracion> findByidpubli(Integer idpubli);
   public List<Valoracion> findByiduser(Integer iduser);
}
