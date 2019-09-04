package com.txoksue.bustime.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@ToString
public class BusDTO {

	@JsonProperty("data")
	private List<ArriveInfoDTO> data;

	@JsonGetter("data")
	public List<ArriveInfoDTO> getData() {
		return data;
	}

	public void setData(List<ArriveInfoDTO> data) {
		this.data = data;
	}
	
	
}
