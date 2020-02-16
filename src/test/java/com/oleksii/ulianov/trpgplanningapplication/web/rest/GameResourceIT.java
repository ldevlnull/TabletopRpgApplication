package com.oleksii.ulianov.trpgplanningapplication.web.rest;

import com.oleksii.ulianov.trpgplanningapplication.TrpgPlanningApplicationApp;
import com.oleksii.ulianov.trpgplanningapplication.domain.Game;
import com.oleksii.ulianov.trpgplanningapplication.repository.GameRepository;
import com.oleksii.ulianov.trpgplanningapplication.service.GameService;
import com.oleksii.ulianov.trpgplanningapplication.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.oleksii.ulianov.trpgplanningapplication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.oleksii.ulianov.trpgplanningapplication.domain.enumeration.GameStatus;
/**
 * Integration tests for the {@link GameResource} REST controller.
 */
@SpringBootTest(classes = TrpgPlanningApplicationApp.class)
public class GameResourceIT {

    private static final String DEFAULT_GAME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GAME_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_PLAY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLAY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PLAYERS_LIMIT = 1;
    private static final Integer UPDATED_PLAYERS_LIMIT = 2;

    private static final String DEFAULT_PICTURE_URL = "AAAAAAAAAA";
    private static final String UPDATED_PICTURE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final GameStatus DEFAULT_STATUS = GameStatus.PENDING;
    private static final GameStatus UPDATED_STATUS = GameStatus.IN_PROGRESS;

    @Autowired
    private GameRepository gameRepository;

    @Mock
    private GameRepository gameRepositoryMock;

    @Mock
    private GameService gameServiceMock;

    @Autowired
    private GameService gameService;

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

    private MockMvc restGameMockMvc;

    private Game game;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GameResource gameResource = new GameResource(gameService);
        this.restGameMockMvc = MockMvcBuilders.standaloneSetup(gameResource)
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
    public static Game createEntity(EntityManager em) {
        Game game = new Game()
            .gameName(DEFAULT_GAME_NAME)
            .playDate(DEFAULT_PLAY_DATE)
            .playersLimit(DEFAULT_PLAYERS_LIMIT)
            .pictureURL(DEFAULT_PICTURE_URL)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return game;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createUpdatedEntity(EntityManager em) {
        Game game = new Game()
            .gameName(UPDATED_GAME_NAME)
            .playDate(UPDATED_PLAY_DATE)
            .playersLimit(UPDATED_PLAYERS_LIMIT)
            .pictureURL(UPDATED_PICTURE_URL)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return game;
    }

    @BeforeEach
    public void initTest() {
        game = createEntity(em);
    }

    @Test
    @Transactional
    public void createGame() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game
        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isCreated());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate + 1);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getGameName()).isEqualTo(DEFAULT_GAME_NAME);
        assertThat(testGame.getPlayDate()).isEqualTo(DEFAULT_PLAY_DATE);
        assertThat(testGame.getPlayersLimit()).isEqualTo(DEFAULT_PLAYERS_LIMIT);
        assertThat(testGame.getPictureURL()).isEqualTo(DEFAULT_PICTURE_URL);
        assertThat(testGame.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGame.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createGameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game with an existing ID
        game.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGameNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setGameName(null);

        // Create the Game, which fails.

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlayDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setPlayDate(null);

        // Create the Game, which fails.

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRepository.findAll().size();
        // set the field null
        game.setStatus(null);

        // Create the Game, which fails.

        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get all the gameList
        restGameMockMvc.perform(get("/api/games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(game.getId().intValue())))
            .andExpect(jsonPath("$.[*].gameName").value(hasItem(DEFAULT_GAME_NAME)))
            .andExpect(jsonPath("$.[*].playDate").value(hasItem(DEFAULT_PLAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].playersLimit").value(hasItem(DEFAULT_PLAYERS_LIMIT)))
            .andExpect(jsonPath("$.[*].pictureURL").value(hasItem(DEFAULT_PICTURE_URL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGamesWithEagerRelationshipsIsEnabled() throws Exception {
        GameResource gameResource = new GameResource(gameServiceMock);
        when(gameServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGameMockMvc = MockMvcBuilders.standaloneSetup(gameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGameMockMvc.perform(get("/api/games?eagerload=true"))
        .andExpect(status().isOk());

        verify(gameServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGamesWithEagerRelationshipsIsNotEnabled() throws Exception {
        GameResource gameResource = new GameResource(gameServiceMock);
            when(gameServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGameMockMvc = MockMvcBuilders.standaloneSetup(gameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGameMockMvc.perform(get("/api/games?eagerload=true"))
        .andExpect(status().isOk());

            verify(gameServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", game.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(game.getId().intValue()))
            .andExpect(jsonPath("$.gameName").value(DEFAULT_GAME_NAME))
            .andExpect(jsonPath("$.playDate").value(DEFAULT_PLAY_DATE.toString()))
            .andExpect(jsonPath("$.playersLimit").value(DEFAULT_PLAYERS_LIMIT))
            .andExpect(jsonPath("$.pictureURL").value(DEFAULT_PICTURE_URL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGame() throws Exception {
        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGame() throws Exception {
        // Initialize the database
        gameService.save(game);

        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game
        Game updatedGame = gameRepository.findById(game.getId()).get();
        // Disconnect from session so that the updates on updatedGame are not directly saved in db
        em.detach(updatedGame);
        updatedGame
            .gameName(UPDATED_GAME_NAME)
            .playDate(UPDATED_PLAY_DATE)
            .playersLimit(UPDATED_PLAYERS_LIMIT)
            .pictureURL(UPDATED_PICTURE_URL)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restGameMockMvc.perform(put("/api/games")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGame)))
            .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getGameName()).isEqualTo(UPDATED_GAME_NAME);
        assertThat(testGame.getPlayDate()).isEqualTo(UPDATED_PLAY_DATE);
        assertThat(testGame.getPlayersLimit()).isEqualTo(UPDATED_PLAYERS_LIMIT);
        assertThat(testGame.getPictureURL()).isEqualTo(UPDATED_PICTURE_URL);
        assertThat(testGame.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGame.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Create the Game

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameMockMvc.perform(put("/api/games")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGame() throws Exception {
        // Initialize the database
        gameService.save(game);

        int databaseSizeBeforeDelete = gameRepository.findAll().size();

        // Delete the game
        restGameMockMvc.perform(delete("/api/games/{id}", game.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
