package main.persistence.repository;

import main.persistence.IDs.IDmeetUp;
import main.persistence.entity.MeetUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoMeetup extends JpaRepository<MeetUp, IDmeetUp> {

}
