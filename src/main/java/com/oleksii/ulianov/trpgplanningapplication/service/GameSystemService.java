package com.oleksii.ulianov.trpgplanningapplication.service;

import com.oleksii.ulianov.trpgplanningapplication.domain.GameSystem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GameSystem}.
 */
public interface GameSystemService {

    /**
     * Save a gameSystem.
     *
     * @param gameSystem the entity to save.
     * @return the persisted entity.
     */
    GameSystem save(GameSystem gameSystem);

    /**
     * Get all the gameSystems.
     *
     * @return the list of entities.
     */
    List<GameSystem> findAll();

    /**
     * Get the "id" gameSystem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameSystem> findOne(Long id);

    /**
     * Delete the "id" gameSystem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
