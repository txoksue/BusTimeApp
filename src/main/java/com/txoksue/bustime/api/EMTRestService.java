package com.txoksue.bustime.api;

import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.BusData;

public interface EMTRestService {
	
	String getAccessToken() throws TimeBusException;
	
	BusData getTimeArrivalBus(String accessToken, Integer stop, Integer busNumber) throws TimeBusException;

}
