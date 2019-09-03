package com.txoksue.bustime.handler;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import static com.amazon.ask.request.Predicates.intentName;
import com.amazon.ask.model.Response;

public class CancelandStopIntentHandler implements RequestHandler {
	
	private static final Logger logger = LogManager.getLogger(CancelandStopIntentHandler.class);

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		
		logger.info("Handling CancelandStopIntent.");
		
		String speechText = "<say-as interpret-as=\"interjection\">Hasta luego.</say-as>";
		return input.getResponseBuilder().withSpeech(speechText).withShouldEndSession(true).build();
	}

}
