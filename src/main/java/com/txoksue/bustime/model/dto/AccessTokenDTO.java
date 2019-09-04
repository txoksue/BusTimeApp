package com.txoksue.bustime.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.NoArgsConstructor;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class TokenData {

	@JsonProperty("data")
	private List<UserInfo> users;

	@JsonGetter("users")
	public List<UserInfo> getUsers() {
		return users;
	}
	
	@JsonSetter("users")
	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}
	
	
	


}
