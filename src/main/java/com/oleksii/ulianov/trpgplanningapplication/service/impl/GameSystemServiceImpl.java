package com.oleksii.ulianov.trpgplanningapplication.service.impl;

import com.oleksii.ulianov.trpgplanningapplication.service.GameSystemService;
import com.oleksii.ulianov.trpgplanningapplication.domain.GameSystem;
import com.oleksii.ulianov.trpgplanningapplication.repository.GameSystemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link GameSystem}.
 */
@Service
@Transactional
public class GameSystemServiceImpl implements GameSystemService {

    private final Logger log = LoggerFactory.getLogger(GameSystemServiceImpl.class);

    private final GameSystemRepository gameSystemRepository;

    public GameSystemServiceImpl(GameSystemRepository gameSystemRepository) {
        this.gameSystemRepository = gameSystemRepository;
    }

    /**
     * Save a gameSystem.
     *
     * @param gameSystem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GameSystem save(GameSystem gameSystem) {
        log.debug("Request to save GameSystem : {}", gameSystem);
        return gameSystemRepository.save(gameSystem);
    }

    /**
     * Get all the gameSystems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GameSystem> findAll() {
        log.debug("Request to get all GameSystems");
        return gameSystemRepository.findAll();
    }

    /**
     * Get one gameSystem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GameSystem> findOne(Long id) {
        log.debug("Request to get GameSystem : {}", id);
        return gameSystemRepository.findById(id);
    }

    /**
     * Delete the gameSystem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameSystem : {}", id);
        gameSystemRepository.deleteById(id);
    }
}
