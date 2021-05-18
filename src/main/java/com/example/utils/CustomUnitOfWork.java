package com.example.utils;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.engine.MDCUnitOfWork;
import org.apache.camel.spi.UnitOfWork;
import org.slf4j.MDC;

public class CustomUnitOfWork extends MDCUnitOfWork {

		static final String CUSTOM_FIELD_NAME = "customField";

		private String customField;

		CustomUnitOfWork(Exchange exchange) {
			super(exchange, exchange.getContext().getInflightRepository(), "", false, false);	
		      
	    	System.out.println("============ CUSTOM UNIT OF WORK +============");

	      
	        
	        
	        if( exchange.getProperty("tracePoint") != null){
	            MDC.put("tracePoint", (String) exchange.getProperty("tracePoint"));
	        }else {
	        	MDC.remove("tracePoint");
	        }	
	        
	        if( exchange.getProperty("status") != null){
	            MDC.put("status", (String) exchange.getProperty("status"));
	        }else {
	        	MDC.remove("status");
	        }
	        
	        
	        if( exchange.getProperty("elapsedTime") != null){
	            MDC.put("elapsedTime", (String) exchange.getProperty("elapsedTime"));
	        }else {
	        	MDC.remove("elapsedTime");
	        }
	        
	        
	    }
		



		@Override
		public UnitOfWork newInstance(Exchange exchange) {
		    return new CustomUnitOfWork(exchange);
		}

		@Override
		public void clear() {
		    super.clear();

		    if (customField != null) {
		        MDC.put(CUSTOM_FIELD_NAME, customField);
		    } else {
		        MDC.remove(CUSTOM_FIELD_NAME);
		    }

		}

		@Override
		public void stop() {
		    super.stop();
		    clear();
		}

		@Override
		public AsyncCallback beforeProcess(Processor processor, Exchange exchange, AsyncCallback callback) {
		    return new CustomMDCCallback(callback);
		}

		private class CustomMDCCallback implements AsyncCallback {

		    private final AsyncCallback delegate;
		    private final String breadcrumbId;
		    private final String exchangeId;
		    private final String messageId;
		    private final String correlationId;
		    private final String routeId;
		    private final String camelContextId;

		    private final String customField;


		    private CustomMDCCallback(AsyncCallback delegate) {
		        this.delegate = delegate;
		        this.exchangeId = MDC.get(MDC_EXCHANGE_ID);
		        this.messageId = MDC.get(MDC_MESSAGE_ID);
		        this.breadcrumbId = MDC.get(MDC_BREADCRUMB_ID);
		        this.correlationId = MDC.get(MDC_CORRELATION_ID);
		        this.camelContextId = MDC.get(MDC_CAMEL_CONTEXT_ID);
		        this.routeId = MDC.get(MDC_ROUTE_ID);

		        this.customField = MDC.get(CUSTOM_FIELD_NAME);

		    }

		    @Override
		    public void done(boolean doneSync) {
		        try {
		            if (!doneSync) {
		                checkAndPut(breadcrumbId, MDC_BREADCRUMB_ID);
		                checkAndPut(exchangeId, MDC_EXCHANGE_ID);
		                checkAndPut(messageId, MDC_MESSAGE_ID);
		                checkAndPut(correlationId, MDC_CORRELATION_ID);
		                checkAndPut(camelContextId, MDC_CAMEL_CONTEXT_ID);

		                checkAndPut(customField, CUSTOM_FIELD_NAME);

		            }
		            checkAndPut(routeId, MDC_ROUTE_ID);

		        } finally {
		            delegate.done(doneSync);
		        }
		    }

		    private void checkAndPut(String value, String fieldName) {
		        if (value != null) {
		            MDC.put(fieldName, value);
		        }
		    }
		}
		}
