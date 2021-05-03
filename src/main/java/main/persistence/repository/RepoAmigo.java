package main.persistence.repository;

import main.persistence.IDs.IDamigo;
import main.persistence.entity.Amigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoAmigo extends JpaRepository<Amigo, IDamigo> {

    //public Amigo findByIds(Integer iduser1, Integer iduser2);

}
