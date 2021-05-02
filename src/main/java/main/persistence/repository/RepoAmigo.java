package main.persistence.repository;

import main.persistence.entity.Amigo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoAmigo extends JpaRepository<Amigo, Integer> {

    public Amigo findByIds(Integer iduser1, Integer iduser2);


}
