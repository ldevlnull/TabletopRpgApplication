package com.oleksii.ulianov.trpgplanningapplication.service;

import com.oleksii.ulianov.trpgplanningapplication.domain.GameTag;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GameTag}.
 */
public interface GameTagService {

    /**
     * Save a gameTag.
     *
     * @param gameTag the entity to save.
     * @return the persisted entity.
     */
    GameTag save(GameTag gameTag);

    /**
     * Get all the gameTags.
     *
     * @return the list of entities.
     */
    List<GameTag> findAll();

    /**
     * Get the "id" gameTag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameTag> findOne(Long id);

    /**
     * Delete the "id" gameTag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
