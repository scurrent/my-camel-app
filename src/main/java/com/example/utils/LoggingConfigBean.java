package com.example.utils;

import org.apache.camel.spi.UnitOfWorkFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfigBean {

	@Bean 
	UnitOfWorkFactory customUntiOfWorkFactory() {
		return exchange -> new CustomUnitOfWork(exchange);
	}
	
}
