package com.txoksue.bustime.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@ToString
@Getter 
@Setter
public class ArriveInfoDTO {
	
	@JsonProperty("Arrive")
	private List<ArriveBusTimeDTO> busTimes;
}
