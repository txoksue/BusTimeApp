package com.txoksue.bustime.services;

import java.util.List;

import com.txoksue.bustime.model.BusData;

public class SpeechServiceImpl implements SpeechService {

	@Override
	public String getSpeechEstimateArrive(List<BusData> timeBusData) {
		
		StringBuffer speechText = new StringBuffer();
		
		timeBusData.forEach(t -> {
			
			String destination = t.getData().get(0).getBusTimes().get(0).getDestination();
			
			String speechDestination = "Para el autobús con dirección " + destination;
			
			speechText.append(speechDestination);
			
			String speechTimeArrive;
			
			int minutes = t.getData().get(0).getBusTimes().get(0).getEstimateArrive() / 60;
			
			if (t.getData().get(0).getBusTimes().get(0).getEstimateArrive()  > 999999) {
				
				speechTimeArrive = "Tranquilo, te quedan más de 20 minutos.";
			
			}else if (minutes >= 15) {

				String coletilla = (minutes > 10)? "todavía tienes tiempo pero no te duermas." : "date prisa, mueve tu culo.";
				
				speechTimeArrive = "te quedan " + minutes + " minutos, " + coletilla;
			
			}else {
				
				speechTimeArrive = "Lo has perdido. La próxima vez muévete más rápido.";
			}
			
			speechText.append(speechTimeArrive);
			
		});
		
		return speechText.toString();
	}

}
