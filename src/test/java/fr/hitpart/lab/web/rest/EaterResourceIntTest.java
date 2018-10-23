package fr.hitpart.lab.web.rest;

import fr.hitpart.lab.AppTestApp;

import fr.hitpart.lab.domain.Eater;
import fr.hitpart.lab.repository.EaterRepository;
import fr.hitpart.lab.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static fr.hitpart.lab.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EaterResource REST controller.
 *
 * @see EaterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestApp.class)
public class EaterResourceIntTest {

    private static final String DEFAULT_PSEUDO = "AAAAAAAAAA";
    private static final String UPDATED_PSEUDO = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private EaterRepository eaterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEaterMockMvc;

    private Eater eater;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EaterResource eaterResource = new EaterResource(eaterRepository);
        this.restEaterMockMvc = MockMvcBuilders.standaloneSetup(eaterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eater createEntity(EntityManager em) {
        Eater eater = new Eater()
            .pseudo(DEFAULT_PSEUDO)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return eater;
    }

    @Before
    public void initTest() {
        eater = createEntity(em);
    }

    @Test
    @Transactional
    public void createEater() throws Exception {
        int databaseSizeBeforeCreate = eaterRepository.findAll().size();

        // Create the Eater
        restEaterMockMvc.perform(post("/api/eaters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eater)))
            .andExpect(status().isCreated());

        // Validate the Eater in the database
        List<Eater> eaterList = eaterRepository.findAll();
        assertThat(eaterList).hasSize(databaseSizeBeforeCreate + 1);
        Eater testEater = eaterList.get(eaterList.size() - 1);
        assertThat(testEater.getPseudo()).isEqualTo(DEFAULT_PSEUDO);
        assertThat(testEater.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEater.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    public void createEaterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eaterRepository.findAll().size();

        // Create the Eater with an existing ID
        eater.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEaterMockMvc.perform(post("/api/eaters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eater)))
            .andExpect(status().isBadRequest());

        // Validate the Eater in the database
        List<Eater> eaterList = eaterRepository.findAll();
        assertThat(eaterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEaters() throws Exception {
        // Initialize the database
        eaterRepository.saveAndFlush(eater);

        // Get all the eaterList
        restEaterMockMvc.perform(get("/api/eaters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eater.getId().intValue())))
            .andExpect(jsonPath("$.[*].pseudo").value(hasItem(DEFAULT_PSEUDO.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getEater() throws Exception {
        // Initialize the database
        eaterRepository.saveAndFlush(eater);

        // Get the eater
        restEaterMockMvc.perform(get("/api/eaters/{id}", eater.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eater.getId().intValue()))
            .andExpect(jsonPath("$.pseudo").value(DEFAULT_PSEUDO.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEater() throws Exception {
        // Get the eater
        restEaterMockMvc.perform(get("/api/eaters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEater() throws Exception {
        // Initialize the database
        eaterRepository.saveAndFlush(eater);

        int databaseSizeBeforeUpdate = eaterRepository.findAll().size();

        // Update the eater
        Eater updatedEater = eaterRepository.findById(eater.getId()).get();
        // Disconnect from session so that the updates on updatedEater are not directly saved in db
        em.detach(updatedEater);
        updatedEater
            .pseudo(UPDATED_PSEUDO)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);

        restEaterMockMvc.perform(put("/api/eaters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEater)))
            .andExpect(status().isOk());

        // Validate the Eater in the database
        List<Eater> eaterList = eaterRepository.findAll();
        assertThat(eaterList).hasSize(databaseSizeBeforeUpdate);
        Eater testEater = eaterList.get(eaterList.size() - 1);
        assertThat(testEater.getPseudo()).isEqualTo(UPDATED_PSEUDO);
        assertThat(testEater.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEater.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEater() throws Exception {
        int databaseSizeBeforeUpdate = eaterRepository.findAll().size();

        // Create the Eater

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEaterMockMvc.perform(put("/api/eaters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eater)))
            .andExpect(status().isBadRequest());

        // Validate the Eater in the database
        List<Eater> eaterList = eaterRepository.findAll();
        assertThat(eaterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEater() throws Exception {
        // Initialize the database
        eaterRepository.saveAndFlush(eater);

        int databaseSizeBeforeDelete = eaterRepository.findAll().size();

        // Get the eater
        restEaterMockMvc.perform(delete("/api/eaters/{id}", eater.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Eater> eaterList = eaterRepository.findAll();
        assertThat(eaterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eater.class);
        Eater eater1 = new Eater();
        eater1.setId(1L);
        Eater eater2 = new Eater();
        eater2.setId(eater1.getId());
        assertThat(eater1).isEqualTo(eater2);
        eater2.setId(2L);
        assertThat(eater1).isNotEqualTo(eater2);
        eater1.setId(null);
        assertThat(eater1).isNotEqualTo(eater2);
    }
}
