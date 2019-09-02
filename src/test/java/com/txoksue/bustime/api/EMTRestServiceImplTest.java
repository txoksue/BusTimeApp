package com.txoksue.bustime.api;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.txoksue.bustime.model.TokenData;

@RunWith(MockitoJUnitRunner.class)
public class EMTRestServiceImplTest {
	
	private static final Logger logger = LogManager.getLogger(EMTRestServiceImplTest.class);

	private static final String JSON_EXAMPLE = "{\"code\": \"01\", \"description\": \"Token 9044ac8d-6ffc-4ea9-95d4-4e60e013edb6 extend  into control-cache\", \"datetime\": \"2019-08-28T18:02:43.577016\", \"data\": [{\"internalUser\": \"dHhva3N1ZTgwQGdtYWlsLmNvbTpaRWo0RWc2MkNjUjlBdGc=\", \"updatedAt\": \"2019-07-15T19:18:41.2030000\", \"userName\": \"txoksue\", \"idUser\": \"2b7e1421-8218-4c1e-b069-f6dd1b90dafe\", \"tokenSecExpiration\": 968, \"email\": \"txoksue80@gmail.com\", \"accessToken\": \"9044ac8d-6ffc-4ea9-95d4-4e60e013edb6\", \"apiCounter\": {\"current\": 61, \"dailyUse\": 150000}}]}";

	private ObjectMapper objectMapper;
	
	@Before
	public void setUp() throws Exception {
		
		objectMapper = new ObjectMapper();
	}
	
	@Test
	public void jsonConvert() {
		
		try {
			
			TokenData response = objectMapper.readValue(JSON_EXAMPLE, TokenData.class);
			
			assertNotNull(response);
			
		} catch (IOException e) {

			logger.error("Error parsing json to TokenData object.");
		}
	}

}
