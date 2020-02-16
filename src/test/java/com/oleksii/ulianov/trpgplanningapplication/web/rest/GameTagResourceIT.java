package com.oleksii.ulianov.trpgplanningapplication.web.rest;

import com.oleksii.ulianov.trpgplanningapplication.TrpgPlanningApplicationApp;
import com.oleksii.ulianov.trpgplanningapplication.domain.GameTag;
import com.oleksii.ulianov.trpgplanningapplication.repository.GameTagRepository;
import com.oleksii.ulianov.trpgplanningapplication.service.GameTagService;
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
 * Integration tests for the {@link GameTagResource} REST controller.
 */
@SpringBootTest(classes = TrpgPlanningApplicationApp.class)
public class GameTagResourceIT {

    private static final String DEFAULT_GAME_TAG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GAME_TAG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GameTagRepository gameTagRepository;

    @Autowired
    private GameTagService gameTagService;

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

    private MockMvc restGameTagMockMvc;

    private GameTag gameTag;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GameTagResource gameTagResource = new GameTagResource(gameTagService);
        this.restGameTagMockMvc = MockMvcBuilders.standaloneSetup(gameTagResource)
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
    public static GameTag createEntity(EntityManager em) {
        GameTag gameTag = new GameTag()
            .gameTagName(DEFAULT_GAME_TAG_NAME)
            .description(DEFAULT_DESCRIPTION);
        return gameTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameTag createUpdatedEntity(EntityManager em) {
        GameTag gameTag = new GameTag()
            .gameTagName(UPDATED_GAME_TAG_NAME)
            .description(UPDATED_DESCRIPTION);
        return gameTag;
    }

    @BeforeEach
    public void initTest() {
        gameTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameTag() throws Exception {
        int databaseSizeBeforeCreate = gameTagRepository.findAll().size();

        // Create the GameTag
        restGameTagMockMvc.perform(post("/api/game-tags")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameTag)))
            .andExpect(status().isCreated());

        // Validate the GameTag in the database
        List<GameTag> gameTagList = gameTagRepository.findAll();
        assertThat(gameTagList).hasSize(databaseSizeBeforeCreate + 1);
        GameTag testGameTag = gameTagList.get(gameTagList.size() - 1);
        assertThat(testGameTag.getGameTagName()).isEqualTo(DEFAULT_GAME_TAG_NAME);
        assertThat(testGameTag.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGameTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameTagRepository.findAll().size();

        // Create the GameTag with an existing ID
        gameTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameTagMockMvc.perform(post("/api/game-tags")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameTag)))
            .andExpect(status().isBadRequest());

        // Validate the GameTag in the database
        List<GameTag> gameTagList = gameTagRepository.findAll();
        assertThat(gameTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGameTagNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameTagRepository.findAll().size();
        // set the field null
        gameTag.setGameTagName(null);

        // Create the GameTag, which fails.

        restGameTagMockMvc.perform(post("/api/game-tags")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameTag)))
            .andExpect(status().isBadRequest());

        List<GameTag> gameTagList = gameTagRepository.findAll();
        assertThat(gameTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGameTags() throws Exception {
        // Initialize the database
        gameTagRepository.saveAndFlush(gameTag);

        // Get all the gameTagList
        restGameTagMockMvc.perform(get("/api/game-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].gameTagName").value(hasItem(DEFAULT_GAME_TAG_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getGameTag() throws Exception {
        // Initialize the database
        gameTagRepository.saveAndFlush(gameTag);

        // Get the gameTag
        restGameTagMockMvc.perform(get("/api/game-tags/{id}", gameTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameTag.getId().intValue()))
            .andExpect(jsonPath("$.gameTagName").value(DEFAULT_GAME_TAG_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingGameTag() throws Exception {
        // Get the gameTag
        restGameTagMockMvc.perform(get("/api/game-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameTag() throws Exception {
        // Initialize the database
        gameTagService.save(gameTag);

        int databaseSizeBeforeUpdate = gameTagRepository.findAll().size();

        // Update the gameTag
        GameTag updatedGameTag = gameTagRepository.findById(gameTag.getId()).get();
        // Disconnect from session so that the updates on updatedGameTag are not directly saved in db
        em.detach(updatedGameTag);
        updatedGameTag
            .gameTagName(UPDATED_GAME_TAG_NAME)
            .description(UPDATED_DESCRIPTION);

        restGameTagMockMvc.perform(put("/api/game-tags")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameTag)))
            .andExpect(status().isOk());

        // Validate the GameTag in the database
        List<GameTag> gameTagList = gameTagRepository.findAll();
        assertThat(gameTagList).hasSize(databaseSizeBeforeUpdate);
        GameTag testGameTag = gameTagList.get(gameTagList.size() - 1);
        assertThat(testGameTag.getGameTagName()).isEqualTo(UPDATED_GAME_TAG_NAME);
        assertThat(testGameTag.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGameTag() throws Exception {
        int databaseSizeBeforeUpdate = gameTagRepository.findAll().size();

        // Create the GameTag

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameTagMockMvc.perform(put("/api/game-tags")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameTag)))
            .andExpect(status().isBadRequest());

        // Validate the GameTag in the database
        List<GameTag> gameTagList = gameTagRepository.findAll();
        assertThat(gameTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameTag() throws Exception {
        // Initialize the database
        gameTagService.save(gameTag);

        int databaseSizeBeforeDelete = gameTagRepository.findAll().size();

        // Delete the gameTag
        restGameTagMockMvc.perform(delete("/api/game-tags/{id}", gameTag.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameTag> gameTagList = gameTagRepository.findAll();
        assertThat(gameTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
