package com.txoksue.bustime.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.txoksue.bustime.model.dto.AccessTokenDTO;
import com.txoksue.bustime.model.dto.BusDTO;

public class EMTRestServiceImpl implements EMTRestService {

	private static final Logger logger = LogManager.getLogger(EMTRestServiceImpl.class);

	private String cultureInfo = "ES";

	private String stopRequired = "N";

	private String estimationsRequired = "Y";

	private String incidencesRequired = "N";

	private String accessTokenUrl = "https://openapi.emtmadrid.es/v1/mobilitylabs/user/login/";

	private String timeBusUrl = "https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/{stop}/arrives/{bus}/";

	@Override
	public String getAccessToken() throws TimeBusException {
		
		logger.info("Getting access token.");

		CloseableHttpClient client = HttpClientBuilder.create().build();

		//email y password son dos variables de entorno añadidas 
		//dentro de la configuración de la Lambda de AWS
		HttpUriRequest request = RequestBuilder.get().setUri(accessTokenUrl)
				.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.setHeader("email", System.getenv("email")).setHeader("password", System.getenv("password")).build();

		try {

			logger.info("Calling Login EMT REST Service: {}", accessTokenUrl);
			
			HttpResponse response = client.execute(request);
			
			logger.info("Result: {}", response.getStatusLine().getStatusCode());

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				String bodyAsString = EntityUtils.toString(response.getEntity());

				AccessTokenDTO tokenData = new ObjectMapper().readValue(bodyAsString, AccessTokenDTO.class);

				client.close();
				
				logger.info("Access token has been got successfully.");

				return tokenData.getUsers().get(0).getAccessToken();
			}

		} catch (IOException e) {

			logger.error("Error getting access token.");
			throw new TimeBusException(e);
		}

		return null;
	}

	@Override
	public BusDTO getTimeArrivalBus(String accessToken, Integer stop, Integer busNumber) throws TimeBusException {
		
		logger.info("Getting time arrival bus information.");

		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		String parsedTimeBusUrl = (timeBusUrl.replace("{stop}", String.valueOf(stop)).replace("{bus}", String.valueOf(busNumber)));

		HttpPost httpPost = new HttpPost(parsedTimeBusUrl);

		try {

			JSONObject bodyRequest = getBodyRequest();

			logger.info("Body request: {}", bodyRequest.toString());
			
			StringEntity entity = new StringEntity(bodyRequest.toString());
			httpPost.setEntity(entity);

			httpPost.setHeader("accessToken", accessToken);
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			
			logger.info("Calling Time arrival bus EMT REST Service: {}", parsedTimeBusUrl);

			CloseableHttpResponse response = client.execute(httpPost);
			
			logger.info("Result: {}", response.getStatusLine().getStatusCode());

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				String bodyAsString = EntityUtils.toString(response.getEntity());

				BusDTO busTimeData = new ObjectMapper().readValue(bodyAsString, BusDTO.class);

				client.close();
				
				logger.info("Time arrival bus information has been got successfully.");
				
				return busTimeData;
			}

		} catch (IOException | TimeBusException e) {

			logger.error("Error getting time arrival bus data.");
			throw new TimeBusException(e);
		}
		
		return null;

	}

	private JSONObject getBodyRequest() throws TimeBusException {

		JSONObject bodyRequest = null;
		
		Date today = new Date();
		String formatToday = new SimpleDateFormat("yyyyMMdd").format(today);

		try {

			bodyRequest = new JSONObject();
			bodyRequest.put("cultureInfo", cultureInfo);
			bodyRequest.put("Text_StopRequired_YN", stopRequired);
			bodyRequest.put("Text_EstimationsRequired_YN", estimationsRequired);
			bodyRequest.put("Text_IncidencesRequired_YN", incidencesRequired);
			bodyRequest.put("TateTime_Referenced_Incidencies_YYYYMMDD", formatToday);

		} catch (JSONException e) {

			throw new TimeBusException("Error parsing json object for body request.");
		}

		return bodyRequest;
	}

}
