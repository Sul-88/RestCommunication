package com.sul.rest.service.messaging.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Sulaiman Abboud
 */
@Component
public class EventPublisher {
	private static final Logger log = LoggerFactory.getLogger(EventPublisher.class);

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void publishPersonTransactionEvent(final Object source, final String message) {
		PersonTransactionEvent event = new PersonTransactionEvent(source, message);
		applicationEventPublisher.publishEvent(event);
		log.debug("Publiching event {}", event.getClass().getName());
	}
}
