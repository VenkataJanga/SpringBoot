package com.hmsapp.model;

import java.util.List;

import com.hmsapp.entity.PatientEntity;

public class PatientResponse {
	private String dataEthnicity;
	private List<AgeResponse> dataAgeMale;
	private List<AgeResponse> dataAgeFemale;
	private List<PatientEntity> locationStatsList;
	private String patientPresentFlag;

	public String getDataEthnicity() {
		return dataEthnicity;
	}

	public void setDataEthnicity(String dataEthnicity) {
		this.dataEthnicity = dataEthnicity;
	}

	public List<AgeResponse> getDataAgeMale() {
		return dataAgeMale;
	}

	public void setDataAgeMale(List<AgeResponse> dataAgeMale) {
		this.dataAgeMale = dataAgeMale;
	}

	public List<AgeResponse> getDataAgeFemale() {
		return dataAgeFemale;
	}

	public void setDataAgeFemale(List<AgeResponse> dataAgeFemale) {
		this.dataAgeFemale = dataAgeFemale;
	}

	public List<PatientEntity> getLocationStatsList() {
		return locationStatsList;
	}

	public void setLocationStatsList(List<PatientEntity> locationStatsList) {
		this.locationStatsList = locationStatsList;
	}

	public String getPatientPresentFlag() {
		return patientPresentFlag;
	}

	public void setPatientPresentFlag(String patientPresentFlag) {
		this.patientPresentFlag = patientPresentFlag;
	}

}
