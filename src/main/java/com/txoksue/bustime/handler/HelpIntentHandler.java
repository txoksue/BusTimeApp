package com.txoksue.bustime.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

public class HelpIntentHandler implements IntentRequestHandler {
	
	private static final Logger logger = LogManager.getLogger(HelpIntentHandler.class);

	@Override
	public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
		return input.matches(intentName("AMAZON.HelpIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
		
		logger.info("Handling HelpIntent");
		
		return input.getResponseBuilder().withSpeech("Puedes preguntarme sobre el tiempo de llegada de los autobuses de la <say-as interpret-as=\"spell-out\">EMT</say-as>. Pero ten en cuenta, que de momento sólo tengo información de los autobuses 175 y 178 para sus paradas en el Hospital Ramón y Cajal.").withShouldEndSession(false).build();
	}

}
