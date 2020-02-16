package com.oleksii.ulianov.trpgplanningapplication.repository;

import com.oleksii.ulianov.trpgplanningapplication.domain.GameTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameTagRepository extends JpaRepository<GameTag, Long> {

}
