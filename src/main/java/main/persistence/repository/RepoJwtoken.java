package main.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import main.persistence.entity.Jwtoken;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoJwtoken extends JpaRepository<Jwtoken, Integer> {

    public Jwtoken findByUsername(String username);

}
