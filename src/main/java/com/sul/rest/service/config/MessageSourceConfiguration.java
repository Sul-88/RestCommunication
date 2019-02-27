package com.sul.rest.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author Sulaiman Abboud
 */
@Configuration
public class MessageSourceConfiguration {

	@Bean("messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource message = new ReloadableResourceBundleMessageSource();
		message.setBasename("classpath:messages/messages");
		message.setDefaultEncoding("UTF-8");
		return message;
	}

}
