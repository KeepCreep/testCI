package fr.hitpart.lab.repository;

import fr.hitpart.lab.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Restaurant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "select distinct restaurant from Restaurant restaurant left join fetch restaurant.meals",
        countQuery = "select count(distinct restaurant) from Restaurant restaurant")
    Page<Restaurant> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct restaurant from Restaurant restaurant left join fetch restaurant.meals")
    List<Restaurant> findAllWithEagerRelationships();

    @Query("select restaurant from Restaurant restaurant left join fetch restaurant.meals where restaurant.id =:id")
    Optional<Restaurant> findOneWithEagerRelationships(@Param("id") Long id);

}
