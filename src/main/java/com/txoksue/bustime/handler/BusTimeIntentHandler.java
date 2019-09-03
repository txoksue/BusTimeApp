package com.txoksue.bustime.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.RequestHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.txoksue.bustime.api.EMTRestService;
import com.txoksue.bustime.api.EMTRestServiceImpl;
import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.Bus;
import com.txoksue.bustime.model.BusData;
import com.txoksue.bustime.model.BusInfoProperties;
import com.txoksue.bustime.services.SpeechService;
import com.txoksue.bustime.services.SpeechServiceImpl;

public class BusTimeIntentHandler implements IntentRequestHandler {

	private static final Logger logger = LogManager.getLogger(BusTimeIntentHandler.class);

	private EMTRestService emtRestService = new EMTRestServiceImpl();

	private SpeechService speechService = new SpeechServiceImpl();

	private ObjectMapper mapper = new ObjectMapper();

	private static final String SPEECH_ERROR = "Ahora mismo no te puedo ayudar. Alguien ha pisado un cable.";

	@Override
	public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
	
		return input.matches(intentName("BusTimeIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

		logger.info("Handling BusTimeIntent.");

		AttributesManager attributesManager = input.getAttributesManager();

		Map<String, Object> sessionAttributes = attributesManager.getSessionAttributes();

		String accessToken = (String) sessionAttributes.get("accessToken");

		logger.info("Getting token from session attributes: {}", accessToken);

		RequestHelper requestHelper = RequestHelper.forHandlerInput(input);

		Optional<String> busNumber = requestHelper.getSlotValue("bus_number");
		
		busNumber.ifPresent(b -> logger.info("Slot: {}", b));

		List<BusData> timesBusData = new ArrayList<>();

		if (busNumber.isPresent()) {
			
			logger.info ("Trying to get time arrive for bus provided as slot: {}", busNumber.get());

			// Parsing busInfoProperties object inside session attributes
			BusInfoProperties busInfoProperties = mapper.convertValue(sessionAttributes.get("busInfoProperties"),
					new TypeReference<BusInfoProperties>() {
					});

			logger.info(busInfoProperties.toString());

			Optional<Bus> bus = busInfoProperties.getBus().stream()
					.filter(b -> b.getNumber().equals(Integer.valueOf(busNumber.get()))).findFirst();


			if (bus.isPresent()) {

				bus.get().getStops().forEach(stop -> {

					try {

						BusData timeBus = emtRestService.getTimeArrivalBus(accessToken, stop, bus.get().getNumber());

						logger.info("Time arrival bus info: {}", timeBus.toString());

						timesBusData.add(timeBus);

					} catch (TimeBusException e) {

						logger.error(e.getMessage());
					}
				});

			}
			
		} else {
			
			logger.info ("Trying to get time arrive for default bus 175.");

			try {

				BusData timeBus = emtRestService.getTimeArrivalBus(accessToken, 955, 175);

				logger.info("Time arrival bus info: {}", timeBus.toString());

				timesBusData.add(timeBus);

			} catch (TimeBusException e) {

				logger.error(e.getMessage());
			}
		}

		if (!timesBusData.isEmpty()) {

			logger.info("Trying to build speech text.");

			String speechText = speechService.buildSpeechEstimateArrive(timesBusData);

			logger.info("Speech text: {}", speechText);

			return input.getResponseBuilder().withSpeech(speechText)
					.withShouldEndSession(false).build();

		} else {

			logger.error("No se ha podido recuperar la información sobre el bus.");
		}
		

		return input.getResponseBuilder().withSpeech(SPEECH_ERROR).withShouldEndSession(false).build();
	}
}
