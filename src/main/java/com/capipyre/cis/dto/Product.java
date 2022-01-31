package com.capipyre.cis.dto;

public class Product {

	private String prodCode;
	private String manufacturer;
	private String createdAt;

	public Product() {
	}

	public Product(String prodCode, String manufacturer, String createdAt) {
		this.prodCode = prodCode;
		this.manufacturer = manufacturer;
		this.createdAt = createdAt;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}
