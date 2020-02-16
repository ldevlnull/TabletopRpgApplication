package com.oleksii.ulianov.trpgplanningapplication.web.rest;

import com.oleksii.ulianov.trpgplanningapplication.domain.GameTag;
import com.oleksii.ulianov.trpgplanningapplication.service.GameTagService;
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
 * REST controller for managing {@link com.oleksii.ulianov.trpgplanningapplication.domain.GameTag}.
 */
@RestController
@RequestMapping("/api")
public class GameTagResource {

    private final Logger log = LoggerFactory.getLogger(GameTagResource.class);

    private static final String ENTITY_NAME = "gameTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameTagService gameTagService;

    public GameTagResource(GameTagService gameTagService) {
        this.gameTagService = gameTagService;
    }

    /**
     * {@code POST  /game-tags} : Create a new gameTag.
     *
     * @param gameTag the gameTag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameTag, or with status {@code 400 (Bad Request)} if the gameTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-tags")
    public ResponseEntity<GameTag> createGameTag(@Valid @RequestBody GameTag gameTag) throws URISyntaxException {
        log.debug("REST request to save GameTag : {}", gameTag);
        if (gameTag.getId() != null) {
            throw new BadRequestAlertException("A new gameTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameTag result = gameTagService.save(gameTag);
        return ResponseEntity.created(new URI("/api/game-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-tags} : Updates an existing gameTag.
     *
     * @param gameTag the gameTag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameTag,
     * or with status {@code 400 (Bad Request)} if the gameTag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameTag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-tags")
    public ResponseEntity<GameTag> updateGameTag(@Valid @RequestBody GameTag gameTag) throws URISyntaxException {
        log.debug("REST request to update GameTag : {}", gameTag);
        if (gameTag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameTag result = gameTagService.save(gameTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameTag.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-tags} : get all the gameTags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameTags in body.
     */
    @GetMapping("/game-tags")
    public List<GameTag> getAllGameTags() {
        log.debug("REST request to get all GameTags");
        return gameTagService.findAll();
    }

    /**
     * {@code GET  /game-tags/:id} : get the "id" gameTag.
     *
     * @param id the id of the gameTag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameTag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-tags/{id}")
    public ResponseEntity<GameTag> getGameTag(@PathVariable Long id) {
        log.debug("REST request to get GameTag : {}", id);
        Optional<GameTag> gameTag = gameTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameTag);
    }

    /**
     * {@code DELETE  /game-tags/:id} : delete the "id" gameTag.
     *
     * @param id the id of the gameTag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-tags/{id}")
    public ResponseEntity<Void> deleteGameTag(@PathVariable Long id) {
        log.debug("REST request to delete GameTag : {}", id);
        gameTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
