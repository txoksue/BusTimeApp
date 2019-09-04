package com.txoksue.bustime.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserInfoDTO {
	
	@JsonProperty
	private String userName;
	
	@JsonProperty
	private Integer tokenSecExpiration;
	
	@JsonProperty
	private String accessToken;
	
	@JsonProperty
	private String updatedAt;
	
	@JsonProperty
	private String internalUser;
	
	@JsonProperty
	private String idUser;
	
	@JsonProperty
	private String email;

}
