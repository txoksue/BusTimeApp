package com.txoksue.bustime.handler;

import static com.amazon.ask.request.Predicates.requestType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.txoksue.bustime.api.EMTRestService;
import com.txoksue.bustime.api.EMTRestServiceImpl;
import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.BusInfoProperties;
import com.txoksue.bustime.service.LoadYamlService;
import com.txoksue.bustime.service.LoadYamlServiceImpl;

public class LaunchRequestHandler implements RequestHandler {

	private static final Logger logger = LogManager.getLogger(LaunchRequestHandler.class);

	private static final String SPEECH_ERROR = "Ahora mismo no te puedo ayudar. Alguien ha pisado un cable.";

	private LoadYamlService loadYamlService = new LoadYamlServiceImpl();

	private EMTRestService emtRestService = new EMTRestServiceImpl();

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(requestType(LaunchRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {

		logger.info("Handling LaunchRequest.");

		AttributesManager attributesManager = input.getAttributesManager();
		Map<String, Object> sessionAttributes = attributesManager.getSessionAttributes();

		BusInfoProperties busPoperties = loadYamlService.loadBusInfo();

		logger.info(busPoperties.toString());

		sessionAttributes.put("busInfoProperties", busPoperties);

		try {

			String accessToken = emtRestService.getAccessToken();

			if (Objects.nonNull(accessToken)) {

				logger.info("Token: {}", accessToken);

				sessionAttributes.put("accessToken", accessToken);

				String repromptText = "";
				String text = "Bienvenido a Tiempo Bus. Tiempo Bus te proporciona información sobre el tiempo de llegada de los autobuses de la <say-as interpret-as=\"spell-out\">EMT</say-as> a una parada en concreto. Pero ten en cuenta, que de momento sólo tengo información de los autobuses 175 y 178 para sus paradas en el Hospital Ramón y Cajal. Además puedes pedir ayuda en cualquier momento y estaré encantada de ayudarte. ¿Qué quieres hacer ahora?";

				return input.getResponseBuilder().withSpeech(text).withShouldEndSession(false).build();
			}

		} catch (TimeBusException e) {

			logger.error("Error recuperando el token. {}", e.getMessage());
		}

		return input.getResponseBuilder().withSpeech(SPEECH_ERROR).withShouldEndSession(true).build();
	}

}
