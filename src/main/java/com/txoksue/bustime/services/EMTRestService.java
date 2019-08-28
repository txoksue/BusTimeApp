package com.txoksue.bustime.services;

import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.ResponseBusTimeData;

public interface EMTRestService {
	
	String getAccessToken() throws TimeBusException;
	
	ResponseBusTimeData getTimeBus(String accessToken) throws TimeBusException;

}
