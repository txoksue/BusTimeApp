package com.txoksue.bustime.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.BusTimeInfo;
import com.txoksue.bustime.model.ResponseBusTimeData;
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

		ResponseEntity<String> responseToken = emtRestService.getAccessToken();

		if (responseToken.getStatusCode().equals(HttpStatus.OK)) {

			ResponseEntity<ResponseBusTimeData> responseTimeBus = null;

			try {

				logger.info("Token: {}",responseToken.getBody());

				responseTimeBus = emtRestService.getTimeBus(responseToken.getBody());

				if (responseTimeBus.getStatusCode().equals(HttpStatus.OK)) {

					List<BusTimeInfo> resp = responseTimeBus.getBody().getData().get(0).getBusTimes();
					
					String bus = resp.get(0).getBus();
					
					logger.info("INFORMACION RECUPERADA");
					logger.info(StringUtils.arrayToCommaDelimitedString(resp.toArray()));
					
					String speechText = "Este es el bus " + bus;
					return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("BusApp", speechText).build();

				} else {

					logger.error("No se ha podido recuperar la información sobre el bus.");
				}

			} catch (TimeBusException e) {

				logger.error(e.getMessage());
			}

		} else {

			logger.error("No se ha podido recuperar un token.");
		}

		return input.getResponseBuilder().withSpeech(SPEECH_ERROR).withSimpleCard("BusApp", SPEECH_ERROR).build();
	}

}
