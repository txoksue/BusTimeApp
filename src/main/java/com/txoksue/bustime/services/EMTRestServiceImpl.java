package com.txoksue.bustime.services;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.BusData;
import com.txoksue.bustime.model.BusInfoProperties;
import com.txoksue.bustime.model.TokenData;


public class EMTRestServiceImpl implements EMTRestService {

	private static final Logger logger = LogManager.getLogger(EMTRestServiceImpl.class);

	private String cultureInfo = "ES";

	private String stopRequired = "N";

	private String estimationsRequired = "Y";

	private String incidencesRequired = "N";

	private String accessTokenUrl = "https://openapi.emtmadrid.es/v1/mobilitylabs/user/login/";

	private String timeBusUrl = "https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/955/arrives/175/";

	@Override
	public String getAccessToken() throws TimeBusException {
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		HttpUriRequest request = RequestBuilder.get()
				  .setUri(accessTokenUrl)
				  .setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				  .setHeader("email", System.getenv("email"))
				  .setHeader("password", System.getenv("password"))
				  .build();

		try {
			
			HttpResponse response = client.execute(request);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				
				String bodyAsString = EntityUtils.toString(response.getEntity());
				
				TokenData tokenData = new ObjectMapper().readValue(bodyAsString, TokenData.class);
				
				client.close();
				
				return tokenData.getUsers().get(0).getAccessToken();
			}
			
		} catch (IOException e) {
		
			logger.error("Error getting token.");
			throw new TimeBusException(e);
		} 
		
		return null;
	}

	@Override
	public BusData getTimeBus(String accessToken) throws TimeBusException {
		
		CloseableHttpClient client = HttpClientBuilder.create().build();

		HttpPost httpPost = new HttpPost(timeBusUrl);
		
		JSONObject bodyRequest = getBodyRequest();

		try {

			StringEntity entity = new StringEntity(bodyRequest.toString());
		    httpPost.setEntity(entity);
		    
		    httpPost.setHeader("accessToken", accessToken);
		    httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
		    
		    CloseableHttpResponse response = client.execute(httpPost);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				String bodyAsString = EntityUtils.toString(response.getEntity());
				
				BusData busTimeData = new ObjectMapper().readValue(bodyAsString, BusData.class);
				
				client.close();
				
				return busTimeData;
			}
  
		} catch (IOException e) {
			
			logger.error("Error getting bus time data.");
			throw new TimeBusException("Error");
		}
	    
		return null;
		
	}
	
	
	@Override
	public BusData getTimeBus(String accessToken, BusInfoProperties busInfo) throws TimeBusException {
		
		CloseableHttpClient client = HttpClientBuilder.create().build();

		HttpPost httpPost = new HttpPost(timeBusUrl);
		
		JSONObject bodyRequest = getBodyRequest();

		try {

			StringEntity entity = new StringEntity(bodyRequest.toString());
		    httpPost.setEntity(entity);
		    
		    httpPost.setHeader("accessToken", accessToken);
		    httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
		    
		    CloseableHttpResponse response = client.execute(httpPost);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				String bodyAsString = EntityUtils.toString(response.getEntity());
				
				BusData busTimeData = new ObjectMapper().readValue(bodyAsString, BusData.class);
				
				client.close();
				
				return busTimeData;
			}
  
		} catch (IOException e) {
			
			logger.error("Error getting bus time data.");
			throw new TimeBusException("Error");
		}
	    
		return null;
		
	}
	
	
	private JSONObject getBodyRequest() throws TimeBusException {
		
		JSONObject bodyRequest = null;

		try {

			bodyRequest = new JSONObject();
			bodyRequest.put("cultureInfo", cultureInfo);
			bodyRequest.put("Text_StopRequired_YN", stopRequired);
			bodyRequest.put("Text_EstimationsRequired_YN", estimationsRequired);
			bodyRequest.put("Text_IncidencesRequired_YN", incidencesRequired);
			bodyRequest.put("TateTime_Referenced_Incidencies_YYYYMMDD", "20190715");

		} catch (JSONException e) {

			throw new TimeBusException("Error parsing json object for body request.");
		}

		return bodyRequest;
	}

}
