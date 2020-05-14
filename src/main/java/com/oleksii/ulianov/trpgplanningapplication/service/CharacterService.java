package com.oleksii.ulianov.trpgplanningapplication.service;

import com.oleksii.ulianov.trpgplanningapplication.domain.Character;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Character}.
 */
public interface CharacterService {

    /**
     * Save a character.
     *
     * @param character the entity to save.
     * @return the persisted entity.
     */
    Character save(Character character);

    /**
     * Get all the characters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Character> findAll(Pageable pageable);

    /**
     * Get all the characters of the given user.
     *
     * @param userId id of the user
     * @return the list of characters
     */
    List<Character> findAllByUserId(long userId);
    /**
     * Get the "id" character.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Character> findOne(Long id);

    /**
     * Delete the "id" character.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
