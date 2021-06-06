package main.persistence.repository;

import main.persistence.entity.Refreshtoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoRefreshtoken extends JpaRepository<Refreshtoken,Integer> {

    public Refreshtoken findByUserid(Integer userid);

}
