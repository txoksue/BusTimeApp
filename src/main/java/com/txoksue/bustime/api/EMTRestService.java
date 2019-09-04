package com.txoksue.bustime.api;

import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.dto.BusDTO;

public interface EMTRestService {
	
	String getAccessToken() throws TimeBusException;
	
	BusDTO getTimeArrivalBus(String accessToken, Integer stop, Integer busNumber) throws TimeBusException;

}
