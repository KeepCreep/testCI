package fr.hitpart.lab.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hitpart.lab.domain.Eater;
import fr.hitpart.lab.repository.EaterRepository;
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
 * REST controller for managing Eater.
 */
@RestController
@RequestMapping("/api")
public class EaterResource {

    private final Logger log = LoggerFactory.getLogger(EaterResource.class);

    private static final String ENTITY_NAME = "eater";

    private final EaterRepository eaterRepository;

    public EaterResource(EaterRepository eaterRepository) {
        this.eaterRepository = eaterRepository;
    }

    /**
     * POST  /eaters : Create a new eater.
     *
     * @param eater the eater to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eater, or with status 400 (Bad Request) if the eater has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eaters")
    @Timed
    public ResponseEntity<Eater> createEater(@RequestBody Eater eater) throws URISyntaxException {
        log.debug("REST request to save Eater : {}", eater);
        if (eater.getId() != null) {
            throw new BadRequestAlertException("A new eater cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Eater result = eaterRepository.save(eater);
        return ResponseEntity.created(new URI("/api/eaters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eaters : Updates an existing eater.
     *
     * @param eater the eater to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eater,
     * or with status 400 (Bad Request) if the eater is not valid,
     * or with status 500 (Internal Server Error) if the eater couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eaters")
    @Timed
    public ResponseEntity<Eater> updateEater(@RequestBody Eater eater) throws URISyntaxException {
        log.debug("REST request to update Eater : {}", eater);
        if (eater.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Eater result = eaterRepository.save(eater);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eater.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eaters : get all the eaters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eaters in body
     */
    @GetMapping("/eaters")
    @Timed
    public List<Eater> getAllEaters() {
        log.debug("REST request to get all Eaters");
        return eaterRepository.findAll();
    }

    /**
     * GET  /eaters/:id : get the "id" eater.
     *
     * @param id the id of the eater to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eater, or with status 404 (Not Found)
     */
    @GetMapping("/eaters/{id}")
    @Timed
    public ResponseEntity<Eater> getEater(@PathVariable Long id) {
        log.debug("REST request to get Eater : {}", id);
        Optional<Eater> eater = eaterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eater);
    }

    /**
     * DELETE  /eaters/:id : delete the "id" eater.
     *
     * @param id the id of the eater to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eaters/{id}")
    @Timed
    public ResponseEntity<Void> deleteEater(@PathVariable Long id) {
        log.debug("REST request to delete Eater : {}", id);

        eaterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
