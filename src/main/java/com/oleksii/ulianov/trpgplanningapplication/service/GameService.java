package com.oleksii.ulianov.trpgplanningapplication.service;

import com.oleksii.ulianov.trpgplanningapplication.domain.Game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Game}.
 */
public interface GameService {

    /**
     * Save a game.
     *
     * @param game the entity to save.
     * @return the persisted entity.
     */
    Game save(Game game);

    /**
     * Get all the games.
     *
     * @return the list of entities.
     */
    List<Game> findAll();

    /**
     * Get all the games with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Game> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" game.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Game> findOne(Long id);

    /**
     * Delete the "id" game.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
