package com.txoksue.bustime.service;

import java.util.List;

import com.txoksue.bustime.model.dto.BusDTO;

public interface SpeechService {

	
	String buildSpeechEstimateArrive(List<BusDTO> timesBusData);
}
