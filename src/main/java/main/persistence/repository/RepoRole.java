package main.persistence.repository;

import main.persistence.IDs.IDrole;
import main.persistence.IDs.IDvaloracion;
import main.persistence.entity.RoleEnum;
import main.persistence.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import main.persistence.entity.Role;

import java.util.List;

@Repository
public interface RepoRole extends JpaRepository<Role, IDrole> {

    public List<Role> findByIduser(Integer iduser);
}
