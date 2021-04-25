package main.persistence.repository;

import main.persistence.IDs.IDMensajes;
import main.persistence.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoMensaje extends JpaRepository<Mensaje,IDMensajes>  {
}
