package com.oleksii.ulianov.trpgplanningapplication.web.rest;

import com.oleksii.ulianov.trpgplanningapplication.TrpgPlanningApplicationApp;
import com.oleksii.ulianov.trpgplanningapplication.domain.GameSystem;
import com.oleksii.ulianov.trpgplanningapplication.repository.GameSystemRepository;
import com.oleksii.ulianov.trpgplanningapplication.service.GameSystemService;
import com.oleksii.ulianov.trpgplanningapplication.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oleksii.ulianov.trpgplanningapplication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GameSystemResource} REST controller.
 */
@SpringBootTest(classes = TrpgPlanningApplicationApp.class)
public class GameSystemResourceIT {

    private static final String DEFAULT_GAME_SYSTEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GAME_SYSTEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GameSystemRepository gameSystemRepository;

    @Autowired
    private GameSystemService gameSystemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restGameSystemMockMvc;

    private GameSystem gameSystem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GameSystemResource gameSystemResource = new GameSystemResource(gameSystemService);
        this.restGameSystemMockMvc = MockMvcBuilders.standaloneSetup(gameSystemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameSystem createEntity(EntityManager em) {
        GameSystem gameSystem = new GameSystem()
            .gameSystemName(DEFAULT_GAME_SYSTEM_NAME)
            .description(DEFAULT_DESCRIPTION);
        return gameSystem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameSystem createUpdatedEntity(EntityManager em) {
        GameSystem gameSystem = new GameSystem()
            .gameSystemName(UPDATED_GAME_SYSTEM_NAME)
            .description(UPDATED_DESCRIPTION);
        return gameSystem;
    }

    @BeforeEach
    public void initTest() {
        gameSystem = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameSystem() throws Exception {
        int databaseSizeBeforeCreate = gameSystemRepository.findAll().size();

        // Create the GameSystem
        restGameSystemMockMvc.perform(post("/api/game-systems")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameSystem)))
            .andExpect(status().isCreated());

        // Validate the GameSystem in the database
        List<GameSystem> gameSystemList = gameSystemRepository.findAll();
        assertThat(gameSystemList).hasSize(databaseSizeBeforeCreate + 1);
        GameSystem testGameSystem = gameSystemList.get(gameSystemList.size() - 1);
        assertThat(testGameSystem.getGameSystemName()).isEqualTo(DEFAULT_GAME_SYSTEM_NAME);
        assertThat(testGameSystem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGameSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameSystemRepository.findAll().size();

        // Create the GameSystem with an existing ID
        gameSystem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameSystemMockMvc.perform(post("/api/game-systems")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameSystem)))
            .andExpect(status().isBadRequest());

        // Validate the GameSystem in the database
        List<GameSystem> gameSystemList = gameSystemRepository.findAll();
        assertThat(gameSystemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGameSystemNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameSystemRepository.findAll().size();
        // set the field null
        gameSystem.setGameSystemName(null);

        // Create the GameSystem, which fails.

        restGameSystemMockMvc.perform(post("/api/game-systems")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameSystem)))
            .andExpect(status().isBadRequest());

        List<GameSystem> gameSystemList = gameSystemRepository.findAll();
        assertThat(gameSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGameSystems() throws Exception {
        // Initialize the database
        gameSystemRepository.saveAndFlush(gameSystem);

        // Get all the gameSystemList
        restGameSystemMockMvc.perform(get("/api/game-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].gameSystemName").value(hasItem(DEFAULT_GAME_SYSTEM_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getGameSystem() throws Exception {
        // Initialize the database
        gameSystemRepository.saveAndFlush(gameSystem);

        // Get the gameSystem
        restGameSystemMockMvc.perform(get("/api/game-systems/{id}", gameSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameSystem.getId().intValue()))
            .andExpect(jsonPath("$.gameSystemName").value(DEFAULT_GAME_SYSTEM_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingGameSystem() throws Exception {
        // Get the gameSystem
        restGameSystemMockMvc.perform(get("/api/game-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameSystem() throws Exception {
        // Initialize the database
        gameSystemService.save(gameSystem);

        int databaseSizeBeforeUpdate = gameSystemRepository.findAll().size();

        // Update the gameSystem
        GameSystem updatedGameSystem = gameSystemRepository.findById(gameSystem.getId()).get();
        // Disconnect from session so that the updates on updatedGameSystem are not directly saved in db
        em.detach(updatedGameSystem);
        updatedGameSystem
            .gameSystemName(UPDATED_GAME_SYSTEM_NAME)
            .description(UPDATED_DESCRIPTION);

        restGameSystemMockMvc.perform(put("/api/game-systems")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameSystem)))
            .andExpect(status().isOk());

        // Validate the GameSystem in the database
        List<GameSystem> gameSystemList = gameSystemRepository.findAll();
        assertThat(gameSystemList).hasSize(databaseSizeBeforeUpdate);
        GameSystem testGameSystem = gameSystemList.get(gameSystemList.size() - 1);
        assertThat(testGameSystem.getGameSystemName()).isEqualTo(UPDATED_GAME_SYSTEM_NAME);
        assertThat(testGameSystem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGameSystem() throws Exception {
        int databaseSizeBeforeUpdate = gameSystemRepository.findAll().size();

        // Create the GameSystem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameSystemMockMvc.perform(put("/api/game-systems")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameSystem)))
            .andExpect(status().isBadRequest());

        // Validate the GameSystem in the database
        List<GameSystem> gameSystemList = gameSystemRepository.findAll();
        assertThat(gameSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameSystem() throws Exception {
        // Initialize the database
        gameSystemService.save(gameSystem);

        int databaseSizeBeforeDelete = gameSystemRepository.findAll().size();

        // Delete the gameSystem
        restGameSystemMockMvc.perform(delete("/api/game-systems/{id}", gameSystem.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameSystem> gameSystemList = gameSystemRepository.findAll();
        assertThat(gameSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
