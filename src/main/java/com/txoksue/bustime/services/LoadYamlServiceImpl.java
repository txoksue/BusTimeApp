package com.txoksue.bustime.services;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class LoadYamlServiceImpl implements LoadYamlService {

	@Override
	public Map<String, Object> load() {
		
		Yaml yaml = new Yaml();
		
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("properties.yml");
		
		return yaml.load(inputStream);
	}

}