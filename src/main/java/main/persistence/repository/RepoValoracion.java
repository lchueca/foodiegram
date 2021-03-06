package main.persistence.repository;

import main.persistence.IDs.IDvaloracion;
import main.persistence.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepoValoracion extends JpaRepository<Valoracion, IDvaloracion>{

   public List<Valoracion> findByIdpubli(Integer idpubli);
   public List<Valoracion> findByIduser(Integer iduser);
}
