package com.oleksii.ulianov.trpgplanningapplication.web.rest;

import com.oleksii.ulianov.trpgplanningapplication.domain.GameSystem;
import com.oleksii.ulianov.trpgplanningapplication.service.GameSystemService;
import com.oleksii.ulianov.trpgplanningapplication.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.oleksii.ulianov.trpgplanningapplication.domain.GameSystem}.
 */
@RestController
@RequestMapping("/api")
public class GameSystemResource {

    private final Logger log = LoggerFactory.getLogger(GameSystemResource.class);

    private static final String ENTITY_NAME = "gameSystem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameSystemService gameSystemService;

    public GameSystemResource(GameSystemService gameSystemService) {
        this.gameSystemService = gameSystemService;
    }

    /**
     * {@code POST  /game-systems} : Create a new gameSystem.
     *
     * @param gameSystem the gameSystem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameSystem, or with status {@code 400 (Bad Request)} if the gameSystem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-systems")
    public ResponseEntity<GameSystem> createGameSystem(@Valid @RequestBody GameSystem gameSystem) throws URISyntaxException {
        log.debug("REST request to save GameSystem : {}", gameSystem);
        if (gameSystem.getId() != null) {
            throw new BadRequestAlertException("A new gameSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameSystem result = gameSystemService.save(gameSystem);
        return ResponseEntity.created(new URI("/api/game-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-systems} : Updates an existing gameSystem.
     *
     * @param gameSystem the gameSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameSystem,
     * or with status {@code 400 (Bad Request)} if the gameSystem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-systems")
    public ResponseEntity<GameSystem> updateGameSystem(@Valid @RequestBody GameSystem gameSystem) throws URISyntaxException {
        log.debug("REST request to update GameSystem : {}", gameSystem);
        if (gameSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameSystem result = gameSystemService.save(gameSystem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameSystem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-systems} : get all the gameSystems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameSystems in body.
     */
    @GetMapping("/game-systems")
    public List<GameSystem> getAllGameSystems() {
        log.debug("REST request to get all GameSystems");
        return gameSystemService.findAll();
    }

    /**
     * {@code GET  /game-systems/:id} : get the "id" gameSystem.
     *
     * @param id the id of the gameSystem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameSystem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-systems/{id}")
    public ResponseEntity<GameSystem> getGameSystem(@PathVariable Long id) {
        log.debug("REST request to get GameSystem : {}", id);
        Optional<GameSystem> gameSystem = gameSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameSystem);
    }

    /**
     * {@code DELETE  /game-systems/:id} : delete the "id" gameSystem.
     *
     * @param id the id of the gameSystem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-systems/{id}")
    public ResponseEntity<Void> deleteGameSystem(@PathVariable Long id) {
        log.debug("REST request to delete GameSystem : {}", id);
        gameSystemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
