package com.txoksue.bustime.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class BusInfoProperties {

	private List<Bus> bus;
}
