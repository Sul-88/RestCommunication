package com.sul.person.service.messaging.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Sulaiman Abboud
 */
@Component
public class JmsMessageSender {
	private static final Logger log = LoggerFactory.getLogger(JmsMessageSender.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendJmsMEssage(String message) {
		log.info("Sending message to queue {}", JmsConstants.TRANSACTION_QUEUE);
		jmsTemplate.convertAndSend(JmsConstants.TRANSACTION_QUEUE, message);
	}

}
