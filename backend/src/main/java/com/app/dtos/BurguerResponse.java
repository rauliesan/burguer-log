package com.app.dtos;

public class BurguerResponse {
	
	private Long id;
	
	private String name;
	
	private String address;
	
	private String town;
	
	private String email;
	
	private Integer mesas;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMesas() {
		return mesas;
	}

	public void setMesas(Integer mesas) {
		this.mesas = mesas;
	}
	
}
