package com.example.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.processor.LoggerInterceptorTwo;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter2 extends RouteBuilder {

	public static final Logger Logger = LoggerFactory.getLogger(MySpringBootRouter2.class);
	
//	@Autowired
//	LoggerInterceptorTwo loggerInterceptorTwo;
	
    @Override
    public void configure() {
    	
    	
    	
        from("timer:hello?period={{timer2.period}}")
        .process("loggerInterceptorStart")
        .routeId("hello2")
        	.log(LoggingLevel.INFO, Logger, "Router 2 start")
            .transform().method("myBean2", "saySomething")
            .filter(simple("${body} contains 'foo'"))
                .to("log:foo")
            .log(LoggingLevel.INFO, Logger, "Router 2 end")    
            .end()
            .to("stream:out")
            .process("loggerInterceptorEnd");

    }

}
