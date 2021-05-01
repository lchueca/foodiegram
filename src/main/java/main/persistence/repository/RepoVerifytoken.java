package main.persistence.repository;


import main.persistence.entity.Verifytoken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoVerifytoken extends JpaRepository<Verifytoken, Integer> {

    Verifytoken findBytoken(Integer token);

}
