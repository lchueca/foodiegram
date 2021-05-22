package main.persistence.repository;

import main.persistence.entity.Patrocinio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoPatrocinio extends JpaRepository<Patrocinio, Integer> {

    Patrocinio findById(Integer id);
}
