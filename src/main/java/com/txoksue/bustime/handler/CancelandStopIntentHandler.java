package com.txoksue.bustime.handler;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import static com.amazon.ask.request.Predicates.intentName;
import com.amazon.ask.model.Response;

public class CancelandStopIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String speechText = "Venga hasta luego";
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("BusApp", speechText).build();
	}

}
