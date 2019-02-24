package com.sul.person.service.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sul.person.service.dto.PersonDTO;
import com.sul.person.service.dto.PersonError;
import com.sul.person.service.messaging.event.EventPublisher;
import com.sul.person.service.repository.PersonJpaRepository;

/**
 * @author Sulaiman Abboud
 */
@RestController
@RequestMapping("/api/person")
public class PersonRestController {
	private static final Logger log = LoggerFactory.getLogger(PersonRestController.class);

	private PersonJpaRepository personJpaRepository;

	@Autowired
	private EventPublisher applicationEventPublisher;

	/**
	 * 
	 * @return {@link ResponseEntity} contains a list of all persons
	 */
	@GetMapping("/")
	public ResponseEntity<List<PersonDTO>> getAllPersons() {
		List<PersonDTO> results = personJpaRepository.findAll();
		if (results.isEmpty()) {
			return new ResponseEntity<List<PersonDTO>>(HttpStatus.NO_CONTENT);
		}
		log.debug("{} of persons has been found", results.size());
		return new ResponseEntity<List<PersonDTO>>(results, HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 * @return {@link ResponseEntity contains found person}
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> getPersonById(@PathVariable("id") final Long id) {
		PersonDTO person = personJpaRepository.getPersonById(id);
		if (person == null) {
			log.error("No person with id {} has not been found", id);
			return new ResponseEntity<PersonDTO>(new PersonError("Person with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		log.debug("A person with id={} not found", id);
		return new ResponseEntity<PersonDTO>(person, HttpStatus.OK);
	}

	/**
	 * 
	 * @param person
	 * @return {@link ResponseEntity} contains the created person
	 */
	@Transactional
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody final PersonDTO person) {
		if (personJpaRepository.getPersonByName(person.getName()) != null) {
			log.error("A person with name {} already exists", person.getName());
			return new ResponseEntity<PersonDTO>(
					new PersonError(
							"UNable to create Person. A person with the name " + person.getName() + " already exists"),
					HttpStatus.CONFLICT);
		}

		personJpaRepository.save(person);
		log.debug("Person with id {} has been created", person.getId());
		applicationEventPublisher.publishPersonTransactionEvent(person, "Person created");
		return new ResponseEntity<PersonDTO>(person, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param id
	 * @return {@link ResponseEntity} that has not content
	 */
	@Transactional
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PersonDTO> deletePerson(@PathVariable("id") final Long id) {
		PersonDTO person = personJpaRepository.getPersonById(id);
		if (person == null) {
			log.error("Person with id {} not found", id);
			return new ResponseEntity<PersonDTO>(
					new PersonError("Unable to delete person. Person with id" + id + "not found"),
					HttpStatus.NOT_FOUND);
		}
		personJpaRepository.delete(person);
		log.debug("Person with id {} has been deleted", id);
		applicationEventPublisher.publishPersonTransactionEvent(person, "Person deleted");
		return new ResponseEntity<PersonDTO>(HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 * @param person
	 * @return {@link ResponseEntity} contains the created person
	 */
	@Transactional
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonDTO> updatePerson(@PathVariable("id") final Long id,
			@Valid @RequestBody final PersonDTO person) {
		PersonDTO currentPerson = personJpaRepository.getPersonById(id);
		if (currentPerson == null) {
			log.error("Person with id {} not found", id);
			return new ResponseEntity<PersonDTO>(
					new PersonError("Unable to upadate person. Person with id" + id + "not found"),
					HttpStatus.NOT_FOUND);
		}
		currentPerson.setName(person.getName());
		personJpaRepository.saveAndFlush(currentPerson);
		log.debug("Person with id {} has been updated", id);
		applicationEventPublisher.publishPersonTransactionEvent(person, "Person updated");
		return new ResponseEntity<PersonDTO>(currentPerson, HttpStatus.OK);
	}

	@Autowired
	public void setPersonJpaRepository(PersonJpaRepository personJpaRepository) {
		this.personJpaRepository = personJpaRepository;
	}

	public void setApplicationEventPublisher(EventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

}