package com.txoksue.bustime.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.txoksue.bustime.model.BusData;

public class SpeechServiceImpl implements SpeechService {
	
	private static final Logger logger = LogManager.getLogger(SpeechServiceImpl.class);

	@Override
	public String buildSpeechEstimateArrive(List<BusData> timeBusData) {
		
		logger.info("Building speech text with estimate arrive bus info.");
		
		StringBuffer speechText = new StringBuffer();
		
		timeBusData.forEach(time -> {
			
			String destination = time.getData().get(0).getBusTimes().get(0).getDestination();
			
			String speechDestination = "Para el autobús con dirección " + destination;
			
			speechText.append(speechDestination).append(".").append(" ");
			
			String speechTimeArrive;
			
			int minutes = time.getData().get(0).getBusTimes().get(0).getEstimateArrive() / 60;
			
			logger.info("Estimate arrive bus for direction {}: {} minutes", time.getData().get(0).getBusTimes().get(0).getDestination(), minutes);
			
			if (time.getData().get(0).getBusTimes().get(0).getEstimateArrive() == 999999) {
				
				speechTimeArrive = "Tranquilo, te quedan más de 20 minutos.";
			
			}else if (minutes > 10) {

				String coletilla = (minutes > 12)? "todavía tienes tiempo pero no te duermas." : "Date prisa, mueve el culo.";
				
				speechTimeArrive = "Te quedan " + minutes + " minutos. " + coletilla;
			
			}else {
				
				speechTimeArrive = "Te quedan " + minutes + " minutos. Eso significa que lo has perdido. La próxima vez muévete más rápido.";
			}
			
			speechText.append(speechTimeArrive).append(" ");
			
		});
		
		logger.info("Speech built.");
		
		return speechText.toString();
	}

}
