package com.txoksue.bustime.handler;

import static com.amazon.ask.request.Predicates.requestType;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;

public class SessionEndedRequestHandler implements RequestHandler {
	
	private static final Logger logger = LogManager.getLogger(SessionEndedRequestHandler.class);

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(requestType(SessionEndedRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		
		logger.info("Handling SessionEndedRequest.");
		
		return input.getResponseBuilder().withSpeech("Venga un saludo.").build();
	}

}
