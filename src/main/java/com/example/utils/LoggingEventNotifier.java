package com.example.utils;

import java.util.Date;
import java.util.EventObject;

import org.apache.camel.Exchange;
import org.apache.camel.spi.CamelEvent;
import org.apache.camel.spi.CamelEvent.ExchangeCompletedEvent;
import org.apache.camel.spi.CamelEvent.ExchangeSentEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LoggingEventNotifier extends EventNotifierSupport {

	public static final Logger Logger = LoggerFactory.getLogger(LoggingEventNotifier.class);

	@Override
	public void notify(CamelEvent event) throws Exception {

		if (event instanceof org.apache.camel.spi.CamelEvent.ExchangeCreatedEvent) {
			// ExchangeCreatedEvent created = (ExchangeCreatedEvent) event;
			// Logger.info(">>> Created " + created. + " millis to send to external system :
			// " + created.getEndpoint());
		}

		if (event instanceof ExchangeSentEvent) {
			ExchangeSentEvent sent = (ExchangeSentEvent) event;
			Logger.info("ELAPSED TIME: " + sent.getTimeTaken() + "ms to send to endpoint: " + sent.getEndpoint());
		}

		if (event instanceof ExchangeCompletedEvent) {
			
			ExchangeCompletedEvent exchangeCompletedEvent = (ExchangeCompletedEvent) event;
			Exchange exchange = exchangeCompletedEvent.getExchange();
			String route = exchange.getFromRouteId();
			long created = exchange.getCreated();

			Date now = new Date();
			
			long elapsed = now.getTime() - created;
			MDC.put("elapsedTime", Long.toString(elapsed));
			Logger.info("COMPLETE TIME: " + elapsed + "ms for exchange on route: " + route);
			MDC.remove("elapsedTime");
			
		}
	}

	public boolean isEnabled(EventObject event) {
		return true;
	}

	protected void doStart() throws Exception {
		// events we want
		setIgnoreExchangeCompletedEvent(false);
		setIgnoreExchangeSentEvents(false);
		// events we don't want
		setIgnoreCamelContextEvents(true);
		setIgnoreServiceEvents(true);
		setIgnoreRouteEvents(true);
		setIgnoreExchangeCreatedEvent(true);
		setIgnoreExchangeFailedEvents(true);
		setIgnoreExchangeRedeliveryEvents(true);
	}

	protected void doStop() throws Exception {
		
	}

}