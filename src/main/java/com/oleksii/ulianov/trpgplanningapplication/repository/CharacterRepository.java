package com.oleksii.ulianov.trpgplanningapplication.repository;

import com.oleksii.ulianov.trpgplanningapplication.domain.Character;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Character entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query("select character from Character character where character.user.login = ?#{principal.username}")
    List<Character> findByUserIsCurrentUser();

}
