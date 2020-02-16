package com.oleksii.ulianov.trpgplanningapplication.web.rest;

import com.oleksii.ulianov.trpgplanningapplication.TrpgPlanningApplicationApp;
import com.oleksii.ulianov.trpgplanningapplication.domain.Character;
import com.oleksii.ulianov.trpgplanningapplication.repository.CharacterRepository;
import com.oleksii.ulianov.trpgplanningapplication.service.CharacterService;
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
 * Integration tests for the {@link CharacterResource} REST controller.
 */
@SpringBootTest(classes = TrpgPlanningApplicationApp.class)
public class CharacterResourceIT {

    private static final String DEFAULT_CHARACTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHARACTER_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ALIVE = false;
    private static final Boolean UPDATED_IS_ALIVE = true;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CharacterService characterService;

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

    private MockMvc restCharacterMockMvc;

    private Character character;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CharacterResource characterResource = new CharacterResource(characterService);
        this.restCharacterMockMvc = MockMvcBuilders.standaloneSetup(characterResource)
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
    public static Character createEntity(EntityManager em) {
        Character character = new Character()
            .characterName(DEFAULT_CHARACTER_NAME)
            .isAlive(DEFAULT_IS_ALIVE);
        return character;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Character createUpdatedEntity(EntityManager em) {
        Character character = new Character()
            .characterName(UPDATED_CHARACTER_NAME)
            .isAlive(UPDATED_IS_ALIVE);
        return character;
    }

    @BeforeEach
    public void initTest() {
        character = createEntity(em);
    }

    @Test
    @Transactional
    public void createCharacter() throws Exception {
        int databaseSizeBeforeCreate = characterRepository.findAll().size();

        // Create the Character
        restCharacterMockMvc.perform(post("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(character)))
            .andExpect(status().isCreated());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeCreate + 1);
        Character testCharacter = characterList.get(characterList.size() - 1);
        assertThat(testCharacter.getCharacterName()).isEqualTo(DEFAULT_CHARACTER_NAME);
        assertThat(testCharacter.isIsAlive()).isEqualTo(DEFAULT_IS_ALIVE);
    }

    @Test
    @Transactional
    public void createCharacterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = characterRepository.findAll().size();

        // Create the Character with an existing ID
        character.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharacterMockMvc.perform(post("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(character)))
            .andExpect(status().isBadRequest());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCharacterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = characterRepository.findAll().size();
        // set the field null
        character.setCharacterName(null);

        // Create the Character, which fails.

        restCharacterMockMvc.perform(post("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(character)))
            .andExpect(status().isBadRequest());

        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCharacters() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Get all the characterList
        restCharacterMockMvc.perform(get("/api/characters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(character.getId().intValue())))
            .andExpect(jsonPath("$.[*].characterName").value(hasItem(DEFAULT_CHARACTER_NAME)))
            .andExpect(jsonPath("$.[*].isAlive").value(hasItem(DEFAULT_IS_ALIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCharacter() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Get the character
        restCharacterMockMvc.perform(get("/api/characters/{id}", character.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(character.getId().intValue()))
            .andExpect(jsonPath("$.characterName").value(DEFAULT_CHARACTER_NAME))
            .andExpect(jsonPath("$.isAlive").value(DEFAULT_IS_ALIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCharacter() throws Exception {
        // Get the character
        restCharacterMockMvc.perform(get("/api/characters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharacter() throws Exception {
        // Initialize the database
        characterService.save(character);

        int databaseSizeBeforeUpdate = characterRepository.findAll().size();

        // Update the character
        Character updatedCharacter = characterRepository.findById(character.getId()).get();
        // Disconnect from session so that the updates on updatedCharacter are not directly saved in db
        em.detach(updatedCharacter);
        updatedCharacter
            .characterName(UPDATED_CHARACTER_NAME)
            .isAlive(UPDATED_IS_ALIVE);

        restCharacterMockMvc.perform(put("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCharacter)))
            .andExpect(status().isOk());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeUpdate);
        Character testCharacter = characterList.get(characterList.size() - 1);
        assertThat(testCharacter.getCharacterName()).isEqualTo(UPDATED_CHARACTER_NAME);
        assertThat(testCharacter.isIsAlive()).isEqualTo(UPDATED_IS_ALIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCharacter() throws Exception {
        int databaseSizeBeforeUpdate = characterRepository.findAll().size();

        // Create the Character

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacterMockMvc.perform(put("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(character)))
            .andExpect(status().isBadRequest());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCharacter() throws Exception {
        // Initialize the database
        characterService.save(character);

        int databaseSizeBeforeDelete = characterRepository.findAll().size();

        // Delete the character
        restCharacterMockMvc.perform(delete("/api/characters/{id}", character.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
