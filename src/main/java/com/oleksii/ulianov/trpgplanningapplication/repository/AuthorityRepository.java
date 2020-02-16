package com.oleksii.ulianov.trpgplanningapplication.repository;

import com.oleksii.ulianov.trpgplanningapplication.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
