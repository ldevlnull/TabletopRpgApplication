package com.oleksii.ulianov.trpgplanningapplication.service.impl;

import com.oleksii.ulianov.trpgplanningapplication.service.CharacterService;
import com.oleksii.ulianov.trpgplanningapplication.domain.Character;
import com.oleksii.ulianov.trpgplanningapplication.repository.CharacterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Character}.
 */
@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {

    private final Logger log = LoggerFactory.getLogger(CharacterServiceImpl.class);

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    /**
     * Save a character.
     *
     * @param character the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Character save(Character character) {
        log.debug("Request to save Character : {}", character);
        return characterRepository.save(character);
    }

    /**
     * Get all the characters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Character> findAll(Pageable pageable) {
        log.debug("Request to get all Characters");
        return characterRepository.findAll(pageable);
    }

    /**
     * Get one character by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Character> findOne(Long id) {
        log.debug("Request to get Character : {}", id);
        return characterRepository.findById(id);
    }

    /**
     * Delete the character by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Character : {}", id);
        characterRepository.deleteById(id);
    }

    @Override
    public List<Character> findAllByUserId(long userId) {
        log.debug("Request to get all Characters by userId = " + userId);
        return characterRepository.findAll().stream()
            .filter(character -> character.getUser().getId() == userId)
            .collect(Collectors.toList());
    }
}
