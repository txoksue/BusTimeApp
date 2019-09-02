package com.txoksue.bustime.services;

import java.util.List;

import com.txoksue.bustime.model.BusData;

public interface SpeechService {

	
	String getSpeechEstimateArrive(List<BusData> timesBusData);
}
