package com.oleksii.ulianov.trpgplanningapplication.repository;

import com.oleksii.ulianov.trpgplanningapplication.domain.GameSystem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameSystemRepository extends JpaRepository<GameSystem, Long> {

}
