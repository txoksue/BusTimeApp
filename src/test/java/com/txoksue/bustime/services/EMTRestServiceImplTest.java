package com.txoksue.bustime.services;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.txoksue.bustime.model.ResponseTokenData;

@RunWith(MockitoJUnitRunner.class)
public class EMTRestServiceImplTest {

	private static final String JSON = "{\"code\": \"01\", \"description\": \"Token 9044ac8d-6ffc-4ea9-95d4-4e60e013edb6 extend  into control-cache\", \"datetime\": \"2019-08-28T18:02:43.577016\", \"data\": [{\"internalUser\": \"dHhva3N1ZTgwQGdtYWlsLmNvbTpaRWo0RWc2MkNjUjlBdGc=\", \"updatedAt\": \"2019-07-15T19:18:41.2030000\", \"userName\": \"txoksue\", \"idUser\": \"2b7e1421-8218-4c1e-b069-f6dd1b90dafe\", \"tokenSecExpiration\": 968, \"email\": \"txoksue80@gmail.com\", \"accessToken\": \"9044ac8d-6ffc-4ea9-95d4-4e60e013edb6\", \"apiCounter\": {\"current\": 61, \"dailyUse\": 150000}}]}";

	private ObjectMapper objectMapper;
	
	@Before
	public void setUp() throws Exception {
		
		objectMapper = new ObjectMapper();
	}
	
	@Test
	public void jsonConvert() {
		
		try {
			ResponseTokenData response = objectMapper.readValue(JSON, ResponseTokenData.class);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
