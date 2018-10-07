package com.intuit.object;

public class Customer {
	private String customerId;
	private String customerName;
	private String address;
	private String houseNum;
	private String street;
	private String city;
	private String state;
	private String zip;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
		String[] add = address.split("-");

		this.houseNum = add[0];
		this.street = add[1];
		this.city = add[2];
		this.state = add[3];
		this.zip = add[4];
	}
	public String getHouseNum() {
		return houseNum;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	

}
