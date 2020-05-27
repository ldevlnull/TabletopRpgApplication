package com.oleksii.ulianov.trpgplanningapplication.service.impl;

import com.oleksii.ulianov.trpgplanningapplication.service.GameService;
import com.oleksii.ulianov.trpgplanningapplication.domain.Game;
import com.oleksii.ulianov.trpgplanningapplication.repository.GameRepository;
import com.oleksii.ulianov.trpgplanningapplication.utils.InstantDayComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

/**
 * Service Implementation for managing {@link Game}.
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private static final InstantDayComparator INSTANT_DAY_COMPARATOR = new InstantDayComparator();

    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Save a game.
     *
     * @param game the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Game save(Game game) {
        log.debug("Request to save Game : {}", game);
        return gameRepository.save(game);
    }

    /**
     * Get all the games.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Game> findAll() {
        log.debug("Request to get all Games");
        return gameRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the games with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Game> findAllWithEagerRelationships(Pageable pageable) {
        return gameRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one game by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Game> findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        return gameRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the game by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.deleteById(id);
    }

    @Override
    public Map<Instant, Set<Game>> findAllGamesGroupedByDays() {
        log.debug("Request to get all Games grouped by dates.");
        Map<Instant, Set<Game>> gamesByDays = new TreeMap<>(INSTANT_DAY_COMPARATOR);
        gameRepository.findAllWithEagerRelationships()
            .stream()
            .sorted(Comparator.comparing(Game::getPlayDate))
            .forEach(game -> {
                if (!gamesByDays.containsKey(game.getPlayDate()))
                    gamesByDays.put(game.getPlayDate(), new LinkedHashSet<>());
                gamesByDays.get(game.getPlayDate()).add(game);
            });
        return gamesByDays;
    }
}
