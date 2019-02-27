package com.sul.rest.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Sulaiman Abboud
 */
@SpringBootApplication
public class RestApplication {
	private static final Logger logger = LogManager.getLogger(RestApplication.class);

	public static void main(String[] args) {
		logger.info("running SpringBootApplication");
		SpringApplication.run(RestApplication.class, args);
	}

}
