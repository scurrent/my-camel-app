package com.example.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.utils.PropertyContainer;



@Component("loggerInterceptorTwo")
public class LoggerInterceptorTwo implements Processor {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptorTwo.class);


	@Autowired
	PropertyContainer properties;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		
		long start = System.currentTimeMillis();
		
	
		exchange.setProperty("startms", start);
		MDC.put("sourceSystem", properties.getSourceSystem());
		MDC.put("environment", properties.getEnv());
		MDC.put("tracePoint", "START");
		
		System.out.println("=====Logger Interceptor TWO=========");
		
	
	
}



	
	
	
	
}
