package com.br.questqudarix.infra.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
	
	private String lastName;
	private String firstName;
	private Integer age;
	
	
	public Contact() {
	}
	
	public Contact(String lastName, String firstName, Integer age) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.age = age;
	}

	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	
	@Override
	public String toString() {
		return lastName + " " + firstName + " " + age;
	}

}
