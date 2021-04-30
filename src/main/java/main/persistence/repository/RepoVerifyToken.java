package main.persistence.repository;


import main.persistence.entity.VerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoVerifyToken extends JpaRepository<VerifyToken, Integer> {

    VerifyToken findBytoken(Integer token);

}
