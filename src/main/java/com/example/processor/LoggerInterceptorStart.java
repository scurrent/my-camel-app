package com.example.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.utils.PropertyContainer;



@Component("loggerInterceptorStart")
public class LoggerInterceptorStart implements Processor {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptorStart.class);


	@Autowired
	PropertyContainer properties;
	
	@Override
	public void process(Exchange exchange) throws Exception {

				
		MDC.put("tracePoint", "START");
		System.out.println("===== Start Route Interceptor=========");
		logger.info("Starting  Route");
		MDC.remove("tracePoint");
	
}



	
	
	
	
}
