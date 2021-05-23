package main.persistence.repository;

import main.persistence.entity.PubliJOINUser;
import main.persistence.entity.PubliJOINUserJOINAmigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoPubliJOINUserJOINAmigo extends JpaRepository<PubliJOINUserJOINAmigo, Integer> {


}
