package fr.hitpart.lab.web.rest;

import fr.hitpart.lab.AppTestApp;

import fr.hitpart.lab.domain.Command;
import fr.hitpart.lab.repository.CommandRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static fr.hitpart.lab.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommandResource REST controller.
 *
 * @see CommandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestApp.class)
public class CommandResourceIntTest {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommandMockMvc;

    private Command command;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommandResource commandResource = new CommandResource(commandRepository);
        this.restCommandMockMvc = MockMvcBuilders.standaloneSetup(commandResource)
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
    public static Command createEntity(EntityManager em) {
        Command command = new Command()
            .creationDate(DEFAULT_CREATION_DATE)
            .name(DEFAULT_NAME);
        return command;
    }

    @Before
    public void initTest() {
        command = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommand() throws Exception {
        int databaseSizeBeforeCreate = commandRepository.findAll().size();

        // Create the Command
        restCommandMockMvc.perform(post("/api/commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(command)))
            .andExpect(status().isCreated());

        // Validate the Command in the database
        List<Command> commandList = commandRepository.findAll();
        assertThat(commandList).hasSize(databaseSizeBeforeCreate + 1);
        Command testCommand = commandList.get(commandList.size() - 1);
        assertThat(testCommand.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCommand.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCommandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandRepository.findAll().size();

        // Create the Command with an existing ID
        command.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandMockMvc.perform(post("/api/commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(command)))
            .andExpect(status().isBadRequest());

        // Validate the Command in the database
        List<Command> commandList = commandRepository.findAll();
        assertThat(commandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommands() throws Exception {
        // Initialize the database
        commandRepository.saveAndFlush(command);

        // Get all the commandList
        restCommandMockMvc.perform(get("/api/commands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(command.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getCommand() throws Exception {
        // Initialize the database
        commandRepository.saveAndFlush(command);

        // Get the command
        restCommandMockMvc.perform(get("/api/commands/{id}", command.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(command.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommand() throws Exception {
        // Get the command
        restCommandMockMvc.perform(get("/api/commands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommand() throws Exception {
        // Initialize the database
        commandRepository.saveAndFlush(command);

        int databaseSizeBeforeUpdate = commandRepository.findAll().size();

        // Update the command
        Command updatedCommand = commandRepository.findById(command.getId()).get();
        // Disconnect from session so that the updates on updatedCommand are not directly saved in db
        em.detach(updatedCommand);
        updatedCommand
            .creationDate(UPDATED_CREATION_DATE)
            .name(UPDATED_NAME);

        restCommandMockMvc.perform(put("/api/commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommand)))
            .andExpect(status().isOk());

        // Validate the Command in the database
        List<Command> commandList = commandRepository.findAll();
        assertThat(commandList).hasSize(databaseSizeBeforeUpdate);
        Command testCommand = commandList.get(commandList.size() - 1);
        assertThat(testCommand.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCommand.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCommand() throws Exception {
        int databaseSizeBeforeUpdate = commandRepository.findAll().size();

        // Create the Command

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandMockMvc.perform(put("/api/commands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(command)))
            .andExpect(status().isBadRequest());

        // Validate the Command in the database
        List<Command> commandList = commandRepository.findAll();
        assertThat(commandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommand() throws Exception {
        // Initialize the database
        commandRepository.saveAndFlush(command);

        int databaseSizeBeforeDelete = commandRepository.findAll().size();

        // Get the command
        restCommandMockMvc.perform(delete("/api/commands/{id}", command.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Command> commandList = commandRepository.findAll();
        assertThat(commandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Command.class);
        Command command1 = new Command();
        command1.setId(1L);
        Command command2 = new Command();
        command2.setId(command1.getId());
        assertThat(command1).isEqualTo(command2);
        command2.setId(2L);
        assertThat(command1).isNotEqualTo(command2);
        command1.setId(null);
        assertThat(command1).isNotEqualTo(command2);
    }
}
