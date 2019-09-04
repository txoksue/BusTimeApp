package com.txoksue.bustime.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.ArrayList;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.txoksue.bustime.api.EMTRestService;
import com.txoksue.bustime.api.EMTRestServiceImpl;
import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.Bus;
import com.txoksue.bustime.model.BusInfoProperties;
import com.txoksue.bustime.model.dto.BusDTO;
import com.txoksue.bustime.service.SpeechService;
import com.txoksue.bustime.service.SpeechServiceImpl;

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

		logger.info("Getting accessToken from session attributes: {}", accessToken);

		RequestHelper requestHelper = RequestHelper.forHandlerInput(input);

		Optional<String> slotBusNumber = requestHelper.getSlotValue("bus_number");

		slotBusNumber.ifPresent(b -> logger.info("Slot: {}", b));

		Integer busNumberToSearch = (slotBusNumber.isPresent())? Integer.valueOf(slotBusNumber.get()) : 175;

		// Parsing busInfoProperties object
		BusInfoProperties busInfoProperties = mapper.convertValue(sessionAttributes.get("busInfoProperties"),
				new TypeReference<BusInfoProperties>() {
				});
	
		logger.info(busInfoProperties.toString());

		logger.info("Trying to get time arrive for bus: {}", busNumberToSearch);

		Optional<Bus> bus = busInfoProperties.getBus().stream()
				.filter(b -> b.getNumber().equals(busNumberToSearch)).findFirst();
		
		List<BusDTO> timesBusData = new ArrayList<>();
			
		if (bus.isPresent()) {
			
			timesBusData = getBusData(accessToken, bus.get());
		}

		if (!timesBusData.isEmpty()) {

			logger.info("Trying to build speech text.");

			String speechText = speechService.buildSpeechEstimateArrive(timesBusData);

			logger.info("Speech text: {}", speechText);

			return input.getResponseBuilder().withSpeech(speechText).withShouldEndSession(false).build();

		} else {

			logger.error("No se ha podido recuperar la información sobre el bus.");
		}

		return input.getResponseBuilder().withSpeech(SPEECH_ERROR).withShouldEndSession(false).build();
	}

	private List<BusDTO> getBusData(String accessToken, Bus bus) {

		List<BusDTO> timesBusData = new ArrayList<>();

		bus.getStops().forEach(stop -> {

			try {

				BusDTO timeBus = emtRestService.getTimeArrivalBus(accessToken, stop, bus.getNumber());

				logger.info("Time arrival bus info: {}", timeBus.toString());
				
				if (Objects.nonNull(timeBus)) {
				
					timesBusData.add(timeBus);
				}

			} catch (TimeBusException e) {

				logger.error(e.getMessage());
			}
		});

		return timesBusData;
	}
}
