package fr.hitpart.lab.web.rest;

import fr.hitpart.lab.AppTestApp;

import fr.hitpart.lab.domain.Meal;
import fr.hitpart.lab.repository.MealRepository;
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
 * Test class for the MealResource REST controller.
 *
 * @see MealResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestApp.class)
public class MealResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_KCAL = 1F;
    private static final Float UPDATED_KCAL = 2F;

    private static final String DEFAULT_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PICTURE = "BBBBBBBBBB";

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMealMockMvc;

    private Meal meal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MealResource mealResource = new MealResource(mealRepository);
        this.restMealMockMvc = MockMvcBuilders.standaloneSetup(mealResource)
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
    public static Meal createEntity(EntityManager em) {
        Meal meal = new Meal()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .kcal(DEFAULT_KCAL)
            .picture(DEFAULT_PICTURE);
        return meal;
    }

    @Before
    public void initTest() {
        meal = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeal() throws Exception {
        int databaseSizeBeforeCreate = mealRepository.findAll().size();

        // Create the Meal
        restMealMockMvc.perform(post("/api/meals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meal)))
            .andExpect(status().isCreated());

        // Validate the Meal in the database
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeCreate + 1);
        Meal testMeal = mealList.get(mealList.size() - 1);
        assertThat(testMeal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMeal.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMeal.getKcal()).isEqualTo(DEFAULT_KCAL);
        assertThat(testMeal.getPicture()).isEqualTo(DEFAULT_PICTURE);
    }

    @Test
    @Transactional
    public void createMealWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mealRepository.findAll().size();

        // Create the Meal with an existing ID
        meal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMealMockMvc.perform(post("/api/meals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meal)))
            .andExpect(status().isBadRequest());

        // Validate the Meal in the database
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMeals() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        // Get all the mealList
        restMealMockMvc.perform(get("/api/meals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].kcal").value(hasItem(DEFAULT_KCAL.doubleValue())))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(DEFAULT_PICTURE.toString())));
    }
    
    @Test
    @Transactional
    public void getMeal() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        // Get the meal
        restMealMockMvc.perform(get("/api/meals/{id}", meal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.kcal").value(DEFAULT_KCAL.doubleValue()))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMeal() throws Exception {
        // Get the meal
        restMealMockMvc.perform(get("/api/meals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeal() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        int databaseSizeBeforeUpdate = mealRepository.findAll().size();

        // Update the meal
        Meal updatedMeal = mealRepository.findById(meal.getId()).get();
        // Disconnect from session so that the updates on updatedMeal are not directly saved in db
        em.detach(updatedMeal);
        updatedMeal
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .kcal(UPDATED_KCAL)
            .picture(UPDATED_PICTURE);

        restMealMockMvc.perform(put("/api/meals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeal)))
            .andExpect(status().isOk());

        // Validate the Meal in the database
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeUpdate);
        Meal testMeal = mealList.get(mealList.size() - 1);
        assertThat(testMeal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMeal.getKcal()).isEqualTo(UPDATED_KCAL);
        assertThat(testMeal.getPicture()).isEqualTo(UPDATED_PICTURE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeal() throws Exception {
        int databaseSizeBeforeUpdate = mealRepository.findAll().size();

        // Create the Meal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMealMockMvc.perform(put("/api/meals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meal)))
            .andExpect(status().isBadRequest());

        // Validate the Meal in the database
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeal() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        int databaseSizeBeforeDelete = mealRepository.findAll().size();

        // Get the meal
        restMealMockMvc.perform(delete("/api/meals/{id}", meal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meal.class);
        Meal meal1 = new Meal();
        meal1.setId(1L);
        Meal meal2 = new Meal();
        meal2.setId(meal1.getId());
        assertThat(meal1).isEqualTo(meal2);
        meal2.setId(2L);
        assertThat(meal1).isNotEqualTo(meal2);
        meal1.setId(null);
        assertThat(meal1).isNotEqualTo(meal2);
    }
}
