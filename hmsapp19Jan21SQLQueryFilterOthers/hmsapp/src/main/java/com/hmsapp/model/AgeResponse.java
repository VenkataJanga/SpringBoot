package com.hmsapp.model;

public class AgeResponse {
	private String ageCategory;
	private Double genderVal;

 
	public AgeResponse(String ageCategory, Double genderVal) {
		super();
		this.ageCategory = ageCategory;
		this.genderVal = genderVal;
	}

	public String getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(String ageCategory) {
		this.ageCategory = ageCategory;
	}

	public Double getGenderVal() {
		return genderVal;
	}

	public void setGenderVal(Double genderVal) {
		this.genderVal = genderVal;
	}

}
