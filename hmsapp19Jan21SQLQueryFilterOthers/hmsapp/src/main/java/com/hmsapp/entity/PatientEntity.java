package com.hmsapp.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "patientdata")
@Table(name = "patientdata")
public class PatientEntity {
	@JsonIgnore
	@Id
	@Column(name = "patientid")
	private String patientid;
	@JsonIgnore
	@Column(name = "sourcesystem")
	private String sourceSystem;
	@JsonIgnore
	@Column(name = "lastname")
	private String lastName;
	@JsonIgnore
	@Column(name = "primaryphonenumber")
	private String primaryPhoneNumber;
	@JsonIgnore
	@Column(name = "primaryemail")
	private String primaryEmail;
	 
	@Column(name = "homeaddressline1")
	private String homeAddressLine1;
	@JsonIgnore
	@Column(name = "homeaddressline2")
	private String homeAddressLine2;
	@JsonIgnore
	@Column(name = "homecity")
	private String homeCity;
	@JsonIgnore
	@Column(name = "homestate")
	private String homeState;
	@JsonIgnore
	@Column(name = "homepostalcode")
	private String homePostalCode;
	 
	@Column(name = "fullname")
	private String fullName;
	@JsonIgnore
	@Column(name = "firstname")
	private String firstName;
	@JsonIgnore
	@Column(name = "middlename")
	private String middleName;
	@JsonIgnore
	@Column(name = "race")
	private String race;
	@JsonIgnore
	@Column(name = "ethnicity")
	private String ethnicity;
	 
	@Column(name = "gender")
	private String gender;
	 
	@Column(name = "age")
	private int age;
	@JsonIgnore
	@Column(name = "birthdate")
	private java.sql.Date birthDate;

	@Transient
	private double latitude;
	@Transient
	private double longitude;
	

	public PatientEntity() {
		super();
	}

	public PatientEntity(String patientid, String sourceSystem, String lastName, String primaryPhoneNumber,
			String primaryEmail, String homeAddressLine1, String homeAddressLine2, String homeCity, String homeState,
			String homePostalCode, String fullName, String firstName, String middleName, String race, String ethnicity,
			String gender, Date birthDate) {
		super();
		this.patientid = patientid;
		this.sourceSystem = sourceSystem;
		this.lastName = lastName;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.primaryEmail = primaryEmail;
		this.homeAddressLine1 = homeAddressLine1;
		this.homeAddressLine2 = homeAddressLine2;
		this.homeCity = homeCity;
		this.homeState = homeState;
		this.homePostalCode = homePostalCode;
		this.fullName = fullName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.race = race;
		this.ethnicity = ethnicity;
		this.gender = gender;
		this.birthDate = birthDate;
	}

	public String getPatientid() {
		return patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}

	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getHomeAddressLine1() {
		return homeAddressLine1;
	}

	public void setHomeAddressLine1(String homeAddressLine1) {
		this.homeAddressLine1 = homeAddressLine1;
	}

	public String getHomeAddressLine2() {
		return homeAddressLine2;
	}

	public void setHomeAddressLine2(String homeAddressLine2) {
		this.homeAddressLine2 = homeAddressLine2;
	}

	public String getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	public String getHomeState() {
		return homeState;
	}

	public void setHomeState(String homeState) {
		this.homeState = homeState;
	}

	public String getHomePostalCode() {
		return homePostalCode;
	}

	public void setHomePostalCode(String homePostalCode) {
		this.homePostalCode = homePostalCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public java.sql.Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(java.sql.Date birthDate) {
		this.birthDate = birthDate;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
