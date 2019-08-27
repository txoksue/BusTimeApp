package com.txoksue.bustime.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
public class BusTimeInfo {
	
	@JsonProperty
	private Integer DistanceBus;
	
	@JsonProperty
	private Object geometry;

	@JsonProperty
	private String bus;
	
	@JsonProperty
	private String positionTypeBus;
	
	@JsonProperty
	private Boolean isHead;
	
	@JsonProperty
	private String destination;
	
	@JsonProperty
	private String stop;
	
	@JsonProperty
	private String line;
	
	@JsonProperty
	private Integer estimateArrive;

	@JsonGetter
	public Boolean getIsHead() {
		return isHead;
	}

	public void setIsHead(Boolean isHead) {
		this.isHead = isHead;
	}

	@JsonGetter
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@JsonGetter
	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	@JsonGetter
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@JsonGetter
	public Integer getEstimateArrive() {
		return estimateArrive;
	}

	public void setEstimateArrive(Integer estimateArrive) {
		this.estimateArrive = estimateArrive;
	}

	@JsonGetter
	public Integer getDistanceBus() {
		return DistanceBus;
	}

	public void setDistanceBus(Integer distanceBus) {
		DistanceBus = distanceBus;
	}

	@JsonGetter
	public Object getGeometry() {
		return geometry;
	}

	public void setGeometry(Object geometry) {
		this.geometry = geometry;
	}

	@JsonGetter
	public String getBus() {
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	@JsonGetter
	public String getPositionTypeBus() {
		return positionTypeBus;
	}

	public void setPositionTypeBus(String positionTypeBus) {
		this.positionTypeBus = positionTypeBus;
	}
	
	
	
	
}
