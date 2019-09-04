package com.txoksue.bustime.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ArriveBusTimeDTO {
	
	@JsonProperty
	private Integer distanceBus;
	
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

}
