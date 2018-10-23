package fr.hitpart.lab.repository;

import fr.hitpart.lab.domain.Eater;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Eater entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EaterRepository extends JpaRepository<Eater, Long> {

}
