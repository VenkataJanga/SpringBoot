package com.hmsapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patientdata")
public class PatientEntity {
	@Id
	@Column(name = "patientid")
	private String patientid;
	@Column(name = "sourcesystem")
	private String sourceSystem;
	@Column(name = "lastname")
	private String lastName;
	@Column(name = "primaryphonenumber")
	private String primaryPhoneNumber;
	@Column(name = "primaryemail")
	private String primaryEmail;
	@Column(name = "homeaddressline1")
	private String homeAddressLine1;
	@Column(name = "homeaddressline2")
	private String homeAddressLine2;
	@Column(name = "homecity")
	private String homeCity;
	@Column(name = "homestate")
	private String homeState;
	@Column(name = "homepostalcode")
	private String homePostalCode;
	@Column(name = "fullname")
	private String fullName;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "middlename")
	private String middleName;
	@Column(name = "race")
	private String race;
	@Column(name = "ethnicity")
	private String ethnicity;
	@Column(name = "gender")
	private String gender;
	@Column(name = "age")
	private int age;
	@Column(name = "birthdate")
	private java.sql.Date birthDate;
	 

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

}
