package com.sul.person.service.error;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.sul.rest.service.config.MessageSourceConfiguration;
import com.sul.rest.service.error.FieldValidationError;
import com.sul.rest.service.error.FieldValidationErrorDetails;
import com.sul.rest.service.error.MessageType;
import com.sul.rest.service.error.ValidationHandler;

/**
 * @author Sulaiman Abboud
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MessageSourceConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class ValidationHandlerTest {

	@Autowired
	private MessageSource messageSource;

	private ValidationHandler validationHanlder;

	@Mock
	private HttpServletRequest request;

	@Mock
	private MethodArgumentNotValidException notValidException;

	@Mock
	private BindingResult bindingResult;

	@Before
	public void setup() {
		validationHanlder = new ValidationHandler(messageSource);
	}

	@Test
	public void handleValidationErrorTest() {
		FieldError fieldError = new FieldError("PersonDTO", "name", "error.name.empty");
		List<FieldError> fieldErrors = new ArrayList<>();
		fieldErrors.add(fieldError);
		when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
		when(notValidException.getBindingResult()).thenReturn(bindingResult);
		when(request.getRequestURI()).thenReturn("/create");

		ResponseEntity<FieldValidationErrorDetails> response = validationHanlder
				.handleValidationError(notValidException, request);

		FieldValidationErrorDetails errorDetails = response.getBody();
		assertEquals(1, errorDetails.getErrors().size());
		List<FieldValidationError> errorList = errorDetails.getErrors().get("name");
		assertNotNull(errorList);
		assertEquals(1, errorList.size());
		FieldValidationError fieldValidationError = errorList.get(0);
		assertEquals("name", fieldValidationError.getField());
		assertEquals("The name is required field", fieldValidationError.getMessage());
		assertEquals(MessageType.ERROR, fieldValidationError.getType());
	}
}
