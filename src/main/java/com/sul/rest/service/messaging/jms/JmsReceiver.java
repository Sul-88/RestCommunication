package com.sul.rest.service.messaging.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author Sulaiman Abboud
 * 
 *         This class is used for testing the messaging sent via jms
 */
@Component
public class JmsReceiver {
	@JmsListener(destination = JmsConstants.TRANSACTION_QUEUE, containerFactory = "myFactory")
	public void receiveMessage(String ransaction) {
		System.out.println("Received <" + ransaction + ">");

	}
}
