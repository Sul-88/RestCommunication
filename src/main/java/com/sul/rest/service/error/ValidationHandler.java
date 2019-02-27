package com.sul.rest.service.error;

/**
 * @author sulaiman
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sulaiman Abboud
 */
@ControllerAdvice
public class ValidationHandler {
	private static final Logger log = LoggerFactory.getLogger(ValidationHandler.class);

	private MessageSource messageSource;

	@Autowired
	public ValidationHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<FieldValidationErrorDetails> handleValidationError(
			MethodArgumentNotValidException notValidException, HttpServletRequest request) {
		FieldValidationErrorDetails errorDetails = new FieldValidationErrorDetails();
		errorDetails.setTimeStamp(new Date().getTime());
		errorDetails.setPath(request.getRequestURI());
		errorDetails.setTitle("Field Validation Error");
		errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDetails.setDevMessage(notValidException.getClass().getName());
		errorDetails.setDetail("Field Validation Failed");

		BindingResult result = notValidException.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			FieldValidationError fieldValidationError = mapToFieldValidationError(fieldError);
			String fieldName = fieldError.getField();
			List<FieldValidationError> fieldValidationErrorList = errorDetails.getErrors().get(fieldName);
			if (fieldValidationErrorList == null) {
				fieldValidationErrorList = new ArrayList<FieldValidationError>();
			}
			fieldValidationErrorList.add(fieldValidationError);
			errorDetails.getErrors().put(fieldName, fieldValidationErrorList);

		}
		log.debug("errorDetails has been created");
		return new ResponseEntity<FieldValidationErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	private FieldValidationError mapToFieldValidationError(FieldError fieldError) {
		FieldValidationError fieldValidationError = new FieldValidationError();
		if (fieldError == null) {
			return fieldValidationError;
		}
		fieldValidationError.setField(fieldError.getField());
		String message = messageSource.getMessage(fieldError.getDefaultMessage(), null,
				LocaleContextHolder.getLocale());
		fieldValidationError.setMessage(message);
		fieldValidationError.setType(MessageType.ERROR);
		return fieldValidationError;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}
}
