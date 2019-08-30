package com.txoksue.bustime.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Bus {

	private Integer number;
	private List<Integer> stops;
}
