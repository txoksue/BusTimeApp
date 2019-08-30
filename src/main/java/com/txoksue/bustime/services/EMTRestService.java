package com.txoksue.bustime.services;

import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.BusData;
import com.txoksue.bustime.model.BusInfoProperties;

public interface EMTRestService {
	
	String getAccessToken() throws TimeBusException;
	
	BusData getTimeBus(String accessToken) throws TimeBusException;
	
	BusData getTimeBus(String accessToken, BusInfoProperties busInfo) throws TimeBusException;

}
