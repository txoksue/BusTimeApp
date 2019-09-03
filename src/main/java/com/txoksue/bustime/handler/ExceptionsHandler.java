package com.txoksue.bustime.handler;

import java.util.Optional;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.Response;

public class ExceptionsHandler implements ExceptionHandler {

	@Override
	public boolean canHandle(HandlerInput input, Throwable throwable) {
		return throwable instanceof AskSdkException;
	}

	@Override
	public Optional<Response> handle(HandlerInput input, Throwable throwable) {
		return input.getResponseBuilder()
				.withSpeech("An error was encountered while handling your request. Try again later.").build();
	}

}
