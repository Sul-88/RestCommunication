package com.sul.rest.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sul.rest.service.dto.PersonDetailDTO;

/**
 * @author Sulaiman Abboud
 */
@Repository
public interface PersonDetailJpaRepository extends JpaRepository<PersonDetailDTO, Long> {

	public PersonDetailDTO findByUsername(String username);
}
