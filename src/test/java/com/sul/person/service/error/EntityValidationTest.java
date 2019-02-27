package com.sul.person.service.error;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sul.rest.service.dto.PersonDTO;

/**
 * @author Sulaiman Abboud
 */
public class EntityValidationTest {

	private Validator validator;
	private ValidatorFactory validatorFactory;

	@Before
	public void setup() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void nullConstraintTest() {
		PersonDTO person = new PersonDTO();
		person.setName(null);
		Set<ConstraintViolation<PersonDTO>> violations = validator.validateProperty(person, "name");
		assertEquals(1, violations.size());
		assertEquals("error.name.empty", violations.iterator().next().getMessage());
	}

	@Test
	public void lengthConstraintTest() {
		PersonDTO person = new PersonDTO();
		person.setName("12");
		Set<ConstraintViolation<PersonDTO>> violations = validator.validateProperty(person, "name");
		assertEquals(1, violations.size());
		assertEquals("error.name.length", violations.iterator().next().getMessage());
		person.setName("testtestjjidjjrreeesd");
		violations = validator.validateProperty(person, "name");
		assertEquals(1, violations.size());
		assertEquals("error.name.length", violations.iterator().next().getMessage());
	}

	@After
	public void teardown() {
		validatorFactory = null;
		validator = null;
	}
}
