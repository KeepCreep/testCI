package fr.hitpart.lab.repository;

import fr.hitpart.lab.domain.Shot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Shot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShotRepository extends JpaRepository<Shot, Long> {

}
