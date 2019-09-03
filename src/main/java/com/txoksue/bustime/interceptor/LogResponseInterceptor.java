package com.txoksue.bustime.interceptor;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.Response;

public class LogResponseInterceptor implements ResponseInterceptor {

	private static final Logger logger = LogManager.getLogger(LogRequestInterceptor.class);

	@Override
	public void process(HandlerInput input, Optional<Response> response) {

		response.ifPresent(r -> logger.info("Response: {}", r.toString()));
	}

}
