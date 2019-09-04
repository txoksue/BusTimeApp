package com.txoksue.bustime.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;


@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
public class UserInfo {
	
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

	@JsonGetter
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonGetter
	public Integer getTokenSecExpiration() {
		return tokenSecExpiration;
	}

	public void setTokenSecExpiration(Integer tokenSecExpiration) {
		this.tokenSecExpiration = tokenSecExpiration;
	}

	@JsonGetter
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@JsonGetter
	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonGetter
	public String getInternalUser() {
		return internalUser;
	}

	public void setInternalUser(String internalUser) {
		this.internalUser = internalUser;
	}

	@JsonGetter
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	@JsonGetter
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
