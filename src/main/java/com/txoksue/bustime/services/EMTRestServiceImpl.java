package com.txoksue.bustime.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.txoksue.bustime.exception.TimeBusException;
import com.txoksue.bustime.model.ResponseBusTimeData;
import com.txoksue.bustime.model.ResponseTokenData;


public class EMTRestServiceImpl implements EMTRestService {

	private static Logger logger = LoggerFactory.getLogger(EMTRestServiceImpl.class);

//	@Value("${busApp.access.token.headers.email}")
//	private String email;
//
//	@Value("${busApp.access.token.headers.password}")
//	private String password;
//
//	@Value("${busApp.bus.time.request.body.cultureInfo}")
//	private String cultureInfo;
//
//	@Value("${busApp.bus.time.request.body.stopRequired}")
//	private String stopRequired;
//
//	@Value("${busApp.bus.time.request.body.estimationsRequired}")
//	private String estimationsRequired;
//
//	@Value("${busApp.bus.time.request.body.incidencesRequired}")
//	private String incidencesRequired;
//
//	@Value("${busApp.urls.accessToken}")
//	private String accessTokenUrl;
//
//	@Value("${busApp.urls.timeBus}")
//	private String timeBusUrl;
	
	
	private String email = "txoksue80@gmail.com";

	private String password = "ZEj4Eg62CcR9Atg";

	private String cultureInfo = "ES";

	private String stopRequired = "N";

	private String estimationsRequired = "Y";

	private String incidencesRequired = "N";

	private String accessTokenUrl = "https://openapi.emtmadrid.es/v1/mobilitylabs/user/login/";

	private String timeBusUrl = "https://openapi.emtmadrid.es/v2/transport/busemtmad/stops/1912/arrives/5/";
	
	
//	@Autowired
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public ResponseEntity<String> getAccessToken() {

		HttpHeaders headers = new HttpHeaders();

		headers.add("email", email);
		headers.add("password", password);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);

		final HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<ResponseTokenData> response = restTemplate.exchange(accessTokenUrl, HttpMethod.GET, entity,
				new ParameterizedTypeReference<ResponseTokenData>() {
				});

		if (response.getBody().getUsers().get(0).getAccessToken() != null) {

			return ResponseEntity.ok().body(response.getBody().getUsers().get(0).getAccessToken());
		}

		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<ResponseBusTimeData> getTimeBus(String accessToken) throws TimeBusException {

		HttpHeaders headers = new HttpHeaders();

		headers.add("accessToken", accessToken);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		JSONObject jsonObject = null;

		try {

			jsonObject = new JSONObject();
			jsonObject.put("cultureInfo", cultureInfo);
			jsonObject.put("Text_StopRequired_YN", stopRequired);
			jsonObject.put("Text_EstimationsRequired_YN", estimationsRequired);
			jsonObject.put("Text_IncidencesRequired_YN", incidencesRequired);
			jsonObject.put("TateTime_Referenced_Incidencies_YYYYMMDD", "20190715");

		} catch (JSONException e) {

			throw new TimeBusException("Error parsing json object.");
		}

		final HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

		ResponseEntity<ResponseBusTimeData> response = restTemplate.exchange(timeBusUrl, HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseBusTimeData>() {});

		// Cogemos la posicion 0 porque es donde esta el objeto con
		// los datos del bus
		if (response.getBody().getData().get(0).getBusTimes() != null) {

			return ResponseEntity.ok().body(response.getBody());
		}


		return ResponseEntity.noContent().build();
	}

}
