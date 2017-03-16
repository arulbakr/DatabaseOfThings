package com.dot.model;

public class Thing {
	
	private String uUID;
	private String name;
	private String createdDate;
	private String attributes;
	
	/**
	 * @return the uuID
	 */
	public String getUUID() {
		return uUID;
	}
	/**
	 * @param uuID the uuID to set
	 */
	public void setUUID(String uUID) {
		this.uUID = uUID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the currentDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the currentDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the attributes
	 */
	public String getAttributes() {
		return attributes;
	}
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
}