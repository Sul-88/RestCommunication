package com.sul.person.service.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.sul.person.service.PersonApplication;
import com.sul.person.service.dto.PersonDTO;
import com.sul.person.service.dto.PersonError;
import com.sul.person.service.messaging.event.EventPublisher;
import com.sul.person.service.repository.PersonJpaRepository;

/**
 * @author sulaiman
 * 
 *         Testing PersonRestController: Database must be connected
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonRestControllerIntegrationTest {

	@Spy
	private PersonRestController restController;

	@Mock
	private PersonJpaRepository personJpaRepository;

	@Mock
	private EventPublisher applicationEventPublisher;

	@Before
	public void setup() {
		restController = new PersonRestController();
		restController.setPersonJpaRepository(personJpaRepository);
		restController.setApplicationEventPublisher(applicationEventPublisher);
	}

	@Test
	public void getPersonByIdNotFoundTest() {
		ResponseEntity<PersonDTO> response = restController.getPersonById(11l);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody() instanceof PersonError);
	}

	@Test
	public void getPersonByIdTest() {
		PersonDTO person = new PersonDTO();
		person.setId(11l);
		person.setName("Test");
		when(personJpaRepository.getPersonById(11l)).thenReturn(person);
		ResponseEntity<PersonDTO> response = restController.getPersonById(11l);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		PersonDTO responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals("Test", responseBody.getName());
		assertEquals(new Long(11), responseBody.getId());
	}

	@Test
	public void createPersonTest() {
		PersonDTO person = new PersonDTO();
		person.setId(11l);
		person.setName("Test");
		when(personJpaRepository.getPersonByName("Test")).thenReturn(null);
		ResponseEntity<PersonDTO> response = restController.createPerson(person);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		PersonDTO responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals("Test", responseBody.getName());
		assertEquals(new Long(11), responseBody.getId());
	}

	@Test
	public void createPersonConflictTest() {
		PersonDTO person = new PersonDTO();
		person.setId(11l);
		person.setName("Test");
		when(personJpaRepository.getPersonByName("Test")).thenReturn(new PersonDTO());
		ResponseEntity<PersonDTO> response = restController.createPerson(person);
		assertNotNull(response);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody() instanceof PersonError);
	}

	@Test
	public void updatePersonTest() {
		PersonDTO person = new PersonDTO();
		person.setId(11l);
		person.setName("Test");
		when(personJpaRepository.getPersonById(11l)).thenReturn(person);
		PersonDTO updatedPerson = new PersonDTO();
		// id must not be updated
		updatedPerson.setId(2l);
		updatedPerson.setName("Test2");
		ResponseEntity<PersonDTO> response = restController.updatePerson(11l, updatedPerson);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		PersonDTO responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals("Test2", responseBody.getName());
		assertEquals(new Long(11), responseBody.getId());
	}

	@Test
	public void updatePersonNotFoundTest() {
		PersonDTO personToUpdate = new PersonDTO();
		personToUpdate.setId(11l);
		personToUpdate.setName("Test");
		when(personJpaRepository.getPersonByName("Test")).thenReturn(null);
		ResponseEntity<PersonDTO> response = restController.updatePerson(11l, personToUpdate);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody() instanceof PersonError);
	}

	@Test
	public void getAllPersonsTest() {
		List<PersonDTO> persons = new ArrayList<PersonDTO>();
		persons.add(new PersonDTO());
		when(personJpaRepository.findAll()).thenReturn(persons);
		ResponseEntity<List<PersonDTO>> response = restController.getAllPersons();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List<PersonDTO> responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals(1, responseBody.size());
	}

	@Test
	public void getAllPersonsNotFoundTest() {
		List<PersonDTO> persons = new ArrayList<PersonDTO>();
		when(personJpaRepository.findAll()).thenReturn(persons);
		ResponseEntity<List<PersonDTO>> response = restController.getAllPersons();
		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void deltePersonTest() {
		PersonDTO person = new PersonDTO();
		person.setId(11l);
		when(personJpaRepository.getPersonById(11l)).thenReturn(person);
		ResponseEntity<PersonDTO> response = restController.deletePerson(11l);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		PersonDTO responseBody = response.getBody();
		assertNull(responseBody);
	}

	@Test
	public void deltePersonNotFoundTest() {
		when(personJpaRepository.getPersonById(11l)).thenReturn(null);
		ResponseEntity<PersonDTO> response = restController.deletePerson(11l);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		PersonDTO responseBody = response.getBody();
		assertNotNull(responseBody);
		assertTrue(responseBody instanceof PersonError);
	}

	@After
	public void tearDown() {
		restController = null;
	}

}
