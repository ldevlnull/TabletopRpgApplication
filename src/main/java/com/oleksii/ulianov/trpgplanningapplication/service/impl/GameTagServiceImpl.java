package com.oleksii.ulianov.trpgplanningapplication.service.impl;

import com.oleksii.ulianov.trpgplanningapplication.service.GameTagService;
import com.oleksii.ulianov.trpgplanningapplication.domain.GameTag;
import com.oleksii.ulianov.trpgplanningapplication.repository.GameTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link GameTag}.
 */
@Service
@Transactional
public class GameTagServiceImpl implements GameTagService {

    private final Logger log = LoggerFactory.getLogger(GameTagServiceImpl.class);

    private final GameTagRepository gameTagRepository;

    public GameTagServiceImpl(GameTagRepository gameTagRepository) {
        this.gameTagRepository = gameTagRepository;
    }

    /**
     * Save a gameTag.
     *
     * @param gameTag the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GameTag save(GameTag gameTag) {
        log.debug("Request to save GameTag : {}", gameTag);
        return gameTagRepository.save(gameTag);
    }

    /**
     * Get all the gameTags.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GameTag> findAll() {
        log.debug("Request to get all GameTags");
        return gameTagRepository.findAll();
    }

    /**
     * Get one gameTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GameTag> findOne(Long id) {
        log.debug("Request to get GameTag : {}", id);
        return gameTagRepository.findById(id);
    }

    /**
     * Delete the gameTag by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameTag : {}", id);
        gameTagRepository.deleteById(id);
    }
}
