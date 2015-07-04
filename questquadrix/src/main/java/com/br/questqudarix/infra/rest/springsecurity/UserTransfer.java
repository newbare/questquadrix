package com.br.questqudarix.infra.rest.springsecurity;

import java.util.Map;


public class UserTransfer
{

	private final String name;

	private final Map<String, Boolean> roles;


	public UserTransfer(String userName, Map<String, Boolean> roles)
	{
		this.name = userName;
		this.roles = roles;
	}


	public String getName()
	{
		return this.name;
	}


	public Map<String, Boolean> getRoles()
	{
		return this.roles;
	}

}