package com.sul.person.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sul.person.service.dto.PersonDetailDTO;

/**
 * @author Sulaiman Abboud
 */
@Repository
public interface PersonDetailJpaRepository extends JpaRepository<PersonDetailDTO, Long> {

	public PersonDetailDTO findByUsername(String username);
}
