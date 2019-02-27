package com.sul.rest.service.messaging.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.sul.rest.service.messaging.event.PersonTransactionEvent;
import com.sul.rest.service.messaging.jms.JmsMessageSender;

/**
 * @author Sulaiman Abboud
 */
@Component
public class PersonEventListener {
	private static final Logger log = LogManager.getLogger(PersonEventListener.class);

	@Autowired
	private JmsMessageSender sender;

	@TransactionalEventListener
	public void processEvent(PersonTransactionEvent event) {
		if (sender == null) {
			throw new IllegalArgumentException("JmsMessageSender has not been set");
		}
		try {
			sender.sendJmsMEssage(event.getMessage());
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
