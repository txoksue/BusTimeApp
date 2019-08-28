package com.txoksue.bustime.handler;

import static com.amazon.ask.request.Predicates.requestType;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

public class LaunchRequestHandler implements RequestHandler {
	
	private static final Logger logger = LogManager.getLogger(LaunchRequestHandler.class);

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(requestType(LaunchRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		
		String repromptText = "";
		String text = "Bienvenido a Tiempo bus. ¿En qué puedo ayudarte?";
		return input.getResponseBuilder().withSpeech(text).withReprompt("Hola").withShouldEndSession(false).build();

	}

}
