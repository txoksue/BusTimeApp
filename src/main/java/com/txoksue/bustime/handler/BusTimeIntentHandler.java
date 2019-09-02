package com.txoksue.bustime.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.RequestHelper;
import com.txoksue.bustime.api.EMTRestService;
import com.txoksue.bustime.api.EMTRestServiceImpl;
import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.ArriveBusTime;
import com.txoksue.bustime.model.Bus;
import com.txoksue.bustime.model.BusData;
import com.txoksue.bustime.model.BusInfoProperties;
import com.txoksue.bustime.services.SpeechService;
import com.txoksue.bustime.services.SpeechServiceImpl;

public class BusTimeIntentHandler implements IntentRequestHandler {

	private static final Logger logger = LogManager.getLogger(BusTimeIntentHandler.class);

	private EMTRestService emtRestService = new EMTRestServiceImpl();

	private SpeechService speechService = new SpeechServiceImpl();

	private static final String SPEECH_ERROR = "Ahora mismo no te puedo ayudar. Alguien ha pisado un cable.";

	@Override
	public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
		return input.matches(intentName("BusTimeIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

		AttributesManager attributesManager = input.getAttributesManager();

		Map<String, Object> sessionAttributes = attributesManager.getSessionAttributes();

		String accessToken = (String) sessionAttributes.get("accessToken");

		logger.info("Token from session attributes: {}", accessToken);

		RequestHelper requestHelper = RequestHelper.forHandlerInput(input);

		Optional<String> busNumber = requestHelper.getSlotValue("bus_number");
		
		logger.info("Slot: {}", busNumber.get());

		if (busNumber.isPresent()) {
			
			logger.info("Slot: {}", busNumber.get());

			BusInfoProperties busInfoProperties = (BusInfoProperties) sessionAttributes.get("busInfoProperties");

			Optional<Bus> bus = busInfoProperties.getBus().stream()
					.filter(b -> b.getNumber() == Integer.valueOf(busNumber.get())).findFirst();

			List<BusData> timesBusData = new ArrayList<>();

			bus.get().getStops().forEach(s -> {

				BusData timeBus;

				try {

					timeBus = emtRestService.getTimeArrivalBus(accessToken, s, bus.get().getNumber());

					timesBusData.add(timeBus);

				} catch (TimeBusException e) {

					logger.error(e.getMessage());		
				}

			});

			if (Objects.nonNull(timesBusData) && !timesBusData.isEmpty()) {

				logger.info("INFORMACION RECUPERADA");

				String speechText = speechService.getSpeechEstimateArrive(timesBusData);

				logger.info(speechText);

				return input.getResponseBuilder().withSpeech(speechText).withReprompt("¿Necesitas más ayuda?")
						.withSimpleCard("BusApp", speechText).build();

			} else {

				logger.error("No se ha podido recuperar la información sobre el bus.");
			}
		}

		return input.getResponseBuilder().withSpeech(SPEECH_ERROR).withSimpleCard("BusApp", SPEECH_ERROR).build();
	}
}
