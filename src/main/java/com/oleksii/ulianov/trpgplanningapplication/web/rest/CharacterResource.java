package com.oleksii.ulianov.trpgplanningapplication.web.rest;

import com.oleksii.ulianov.trpgplanningapplication.domain.Character;
import com.oleksii.ulianov.trpgplanningapplication.service.CharacterService;
import com.oleksii.ulianov.trpgplanningapplication.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.oleksii.ulianov.trpgplanningapplication.domain.Character}.
 */
@RestController
@RequestMapping("/api")
public class CharacterResource {

    private final Logger log = LoggerFactory.getLogger(CharacterResource.class);

    private static final String ENTITY_NAME = "character";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CharacterService characterService;

    public CharacterResource(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * {@code POST  /characters} : Create a new character.
     *
     * @param character the character to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new character, or with status {@code 400 (Bad Request)} if the character has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/characters")
    public ResponseEntity<Character> createCharacter(@Valid @RequestBody Character character) throws URISyntaxException {
        log.debug("REST request to save Character : {}", character);
        if (character.getId() != null) {
            throw new BadRequestAlertException("A new character cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Character result = characterService.save(character);
        return ResponseEntity.created(new URI("/api/characters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /characters} : Updates an existing character.
     *
     * @param character the character to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated character,
     * or with status {@code 400 (Bad Request)} if the character is not valid,
     * or with status {@code 500 (Internal Server Error)} if the character couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/characters")
    public ResponseEntity<Character> updateCharacter(@Valid @RequestBody Character character) throws URISyntaxException {
        log.debug("REST request to update Character : {}", character);
        if (character.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Character result = characterService.save(character);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, character.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /characters} : get all the characters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of characters in body.
     */
    @GetMapping("/characters")
    public ResponseEntity<List<Character>> getAllCharacters(Pageable pageable) {
        log.debug("REST request to get a page of Characters");
        Page<Character> page = characterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /characters/user/:userId} : get all the character of the provided user.
     *
     * @param userId the id of the user.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of characters in body.
     */
    @GetMapping("/characters/user/{userId}")
    public ResponseEntity<List<Character>> getAllCharacters(@PathVariable Long userId) {
        log.debug("REST request to get a list of Characters");
        List<Character> list = characterService.findAllByUserId(userId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /characters/:id} : get the "id" character.
     *
     * @param id the id of the character to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the character, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/characters/{id}")
    public ResponseEntity<Character> getCharacter(@PathVariable Long id) {
        log.debug("REST request to get Character : {}", id);
        Optional<Character> character = characterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(character);
    }

    /**
     * {@code DELETE  /characters/:id} : delete the "id" character.
     *
     * @param id the id of the character to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/characters/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        log.debug("REST request to delete Character : {}", id);
        characterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
