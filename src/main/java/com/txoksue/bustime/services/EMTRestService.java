package com.txoksue.bustime.services;

import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.BusData;

public interface EMTRestService {
	
	String getAccessToken() throws TimeBusException;
	
	BusData getTimeBus(String accessToken) throws TimeBusException;

}
