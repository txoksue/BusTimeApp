package com.txoksue.bustime.services;

import com.txoksue.bustime.model.ArriveBusTime;

public class SpeechServiceImpl implements SpeechService {

	@Override
	public String getSpeechEstimateArrive(ArriveBusTime arrive) {
		
		String speechText;
		
		int minutes = arrive.getEstimateArrive() / 60;
		
		if (arrive.getEstimateArrive() > 999999) {
			
			speechText = "¡Tranquilo!, te quedan más de 20 minutos.";
		
		}else if (minutes > 5) {

			String coletilla = (minutes > 10)? "todavía tienes tiempo, no te duermas." : "date prisa, mueve tu culo.";
			
			speechText = "El autobús llegará en " + minutes + " minutos, " + coletilla;
		
		}else {
			
			speechText = "¡Lo has perdido!. La próxima vez muévete más rápido.";
		}
		
		return speechText;
	}

}
