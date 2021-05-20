package com.example.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.processor.LoggerInterceptor;
import com.example.processor.LoggerInterceptorEnd;
import com.example.processor.LoggerInterceptorStart;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

	public static final Logger Logger = LoggerFactory.getLogger(MySpringBootRouter.class);
	
	
	@Autowired
	LoggerInterceptor loggerInterceptor;
	@Autowired
	LoggerInterceptorStart loggerInterceptorStart;
	@Autowired
	LoggerInterceptorEnd loggerInterceptorEnd;
	
    @Override
    public void configure() {
    	
    
    	
        from("timer:hello?period={{timer.period}}").routeId("hello")
        .process(loggerInterceptorStart)
        	.log(LoggingLevel.INFO, Logger, "Router1 start")
            .transform().method("myBean", "saySomething")
            .filter(simple("${body} contains 'foo'"))
                .to("log:foo")
                        .end()
                .log(LoggingLevel.INFO, Logger, "Router1 mid2")
            .to("stream:out")
            .process(loggerInterceptorEnd);

    }

}
