package com.txoksue.bustime.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@ToString
public class BusData {

	@JsonProperty("data")
	private List<ArriveInfo> data;

	@JsonGetter("data")
	public List<ArriveInfo> getData() {
		return data;
	}

	public void setData(List<ArriveInfo> data) {
		this.data = data;
	}
	
	
}
