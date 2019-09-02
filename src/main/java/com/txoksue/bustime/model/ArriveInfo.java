package com.txoksue.bustime.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@ToString
public class ArriveInfo {
	
	@JsonProperty("Arrive")
	private List<ArriveBusTime> busTimes;

	public List<ArriveBusTime> getBusTimes() {
		return busTimes;
	}

	public void setBusTimes(List<ArriveBusTime> busTimes) {
		this.busTimes = busTimes;
	}
	
	
	
}
