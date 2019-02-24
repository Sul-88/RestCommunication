package com.sul.person.service.repository;

/**
 * @author Sulaiman Abboud
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sul.person.service.dto.PersonDTO;

@Repository
public interface PersonJpaRepository extends JpaRepository<PersonDTO, Long> {

	PersonDTO getPersonById(Long id);

	PersonDTO getPersonByName(String name);

	default void save() {

	};

}
