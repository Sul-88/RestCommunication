package com.sul.rest.service.messaging.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Sulaiman Abboud
 */
public class PersonTransactionEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private String message;

	public PersonTransactionEvent(Object source, String message) {
		super(source);
		this.message = message;

	}

	public String getMessage() {
		return message;
	}

}
