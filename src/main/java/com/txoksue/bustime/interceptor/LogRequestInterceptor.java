package com.txoksue.bustime.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;


public class LogRequestInterceptor implements RequestInterceptor {
	
	private static final Logger logger = LogManager.getLogger(LogRequestInterceptor.class);
	
	@Override
	public void process(HandlerInput input) {
        
		logger.info("Request: {}", input.getRequest().toString());
    }

}
