package fr.hitpart.lab.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hitpart.lab.domain.Restaurant;
import fr.hitpart.lab.repository.RestaurantRepository;
import fr.hitpart.lab.web.rest.errors.BadRequestAlertException;
import fr.hitpart.lab.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Restaurant.
 */
@RestController
@RequestMapping("/api")
public class RestaurantResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantResource.class);

    private static final String ENTITY_NAME = "restaurant";

    private final RestaurantRepository restaurantRepository;

    public RestaurantResource(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * POST  /restaurants : Create a new restaurant.
     *
     * @param restaurant the restaurant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restaurant, or with status 400 (Bad Request) if the restaurant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restaurants")
    @Timed
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to save Restaurant : {}", restaurant);
        if (restaurant.getId() != null) {
            throw new BadRequestAlertException("A new restaurant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Restaurant result = restaurantRepository.save(restaurant);
        return ResponseEntity.created(new URI("/api/restaurants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restaurants : Updates an existing restaurant.
     *
     * @param restaurant the restaurant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restaurant,
     * or with status 400 (Bad Request) if the restaurant is not valid,
     * or with status 500 (Internal Server Error) if the restaurant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restaurants")
    @Timed
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to update Restaurant : {}", restaurant);
        if (restaurant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Restaurant result = restaurantRepository.save(restaurant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restaurant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restaurants : get all the restaurants.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of restaurants in body
     */
    @GetMapping("/restaurants")
    @Timed
    public List<Restaurant> getAllRestaurants(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Restaurants");
        return restaurantRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /restaurants/:id : get the "id" restaurant.
     *
     * @param id the id of the restaurant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restaurant, or with status 404 (Not Found)
     */
    @GetMapping("/restaurants/{id}")
    @Timed
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        log.debug("REST request to get Restaurant : {}", id);
        Optional<Restaurant> restaurant = restaurantRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(restaurant);
    }

    /**
     * DELETE  /restaurants/:id : delete the "id" restaurant.
     *
     * @param id the id of the restaurant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restaurants/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        log.debug("REST request to delete Restaurant : {}", id);

        restaurantRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
