package com.txoksue.bustime.service;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.txoksue.bustime.model.BusInfoProperties;


public class LoadYamlServiceImpl implements LoadYamlService {

	@Override
	public BusInfoProperties loadBusInfo() {
		
		Yaml yaml = new Yaml(new Constructor(BusInfoProperties.class));
		
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("properties.yml");
		
		return yaml.load(inputStream);
	}

}