package com.sul.rest.service.repository;

/**
 * @author Sulaiman Abboud
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sul.rest.service.dto.PersonDTO;

@Repository
public interface PersonJpaRepository extends JpaRepository<PersonDTO, Long> {

	PersonDTO getPersonById(Long id);

	PersonDTO getPersonByName(String name);

	default void save() {

	};

}
