package com.txoksue.bustime.services;

import org.springframework.http.ResponseEntity;

import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.ResponseBusTimeData;

public interface EMTRestService {
	
	ResponseEntity<String> getAccessToken();
	
	ResponseEntity<ResponseBusTimeData> getTimeBus(String accessToken) throws TimeBusException;

}
