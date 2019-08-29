package com.txoksue.bustime.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.ArriveBusTime;
import com.txoksue.bustime.model.BusData;
import com.txoksue.bustime.services.EMTRestService;
import com.txoksue.bustime.services.EMTRestServiceImpl;

public class BusTimeIntentHandler implements RequestHandler {

	private static final Logger logger = LogManager.getLogger(BusTimeIntentHandler.class);

	private EMTRestService emtRestService = new EMTRestServiceImpl();

	private static final String SPEECH_ERROR = "Ahora mismo no te puedo ayudar. Alguien ha pisado un cable";

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("BusTimeIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {

		try {
			
			String responseToken = emtRestService.getAccessToken();

			if (Objects.nonNull(responseToken)) {

				BusData responseTimeBus = null;

				logger.info("Token: {}", responseToken);

				responseTimeBus = emtRestService.getTimeBus(responseToken);

				if (Objects.nonNull(responseTimeBus)) {

					List<ArriveBusTime> resp = responseTimeBus.getData().get(0).getBusTimes();

					String bus = resp.get(0).getBus();

					logger.info("INFORMACION RECUPERADA");

					String speechText = "Este es el bus con variables de entorno " + bus;
					return input.getResponseBuilder().withSpeech(speechText).withReprompt("¿Necesitas mas ayuda?")
							.withSimpleCard("BusApp", speechText).build();

				} else {

					logger.error("No se ha podido recuperar la información sobre el bus.");
				}

			} else {

				logger.error("No se ha podido recuperar un token.");
			}

		} catch (TimeBusException e) {

			logger.error(e.getMessage());
		}

		return input.getResponseBuilder().withSpeech(SPEECH_ERROR).withSimpleCard("BusApp", SPEECH_ERROR).build();
	}

}
