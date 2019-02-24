package com.sul.person.service.monitoring;

import java.util.Date;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

/**
 * @author Sulaiman Abboud
 */
public class CustomPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(CustomPerformanceMonitorInterceptor.class);

	public CustomPerformanceMonitorInterceptor() {
	}

	public CustomPerformanceMonitorInterceptor(boolean useDynamicLogger) {
		setUseDynamicLogger(useDynamicLogger);
	}

	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
		String name = createInvocationTraceName(invocation);
		long start = System.currentTimeMillis();
		log.info("Method" + name + " execution started at:" + new Date());
		try {
			return invocation.proceed();
		} finally {
			long end = System.currentTimeMillis();
			long time = end - start;
			log.info("Method " + name + " execution lasted:" + time + " ms");
			log.info("Method " + name + " execution ended at:" + new Date());
			log.info("Method {} execution took {} ms", name, time);
			if (time > 100) {
				log.warn("Method execution took longer than 10 ms.");
			}
		}
	}

}
