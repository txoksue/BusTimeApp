package com.txoksue.bustime.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class BusInfoProperties {

	private Integer number;
	private List<Integer> stops;
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public List<Integer> getStops() {
		return stops;
	}
	public void setStops(List<Integer> stops) {
		this.stops = stops;
	}
	
	
}
