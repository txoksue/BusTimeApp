package com.txoksue.bustime.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ArriveInfo {
	
	@JsonProperty("Arrive")
	private List<BusTimeInfo> busTimes;

	public List<BusTimeInfo> getBusTimes() {
		return busTimes;
	}

	public void setBusTimes(List<BusTimeInfo> busTimes) {
		this.busTimes = busTimes;
	}
	
	
	
}
