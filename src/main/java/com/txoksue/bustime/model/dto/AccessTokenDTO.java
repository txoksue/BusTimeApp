package com.txoksue.bustime.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.NoArgsConstructor;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class AccessTokenDTO {

	@JsonProperty("data")
	private List<UserInfoDTO> users;

	@JsonGetter("users")
	public List<UserInfoDTO> getUsers() {
		return users;
	}
	
	@JsonSetter("users")
	public void setUsers(List<UserInfoDTO> users) {
		this.users = users;
	}

}
