package com.hmsapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SearchParam {
	private String searchBy;
	private String searchText;

	private int startAge;
	private int endAge;
	private String gender;
	private String[] ethnicGroups;
	private String selectedStates;

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public int getAge() {
		return startAge;
	}

	public void setAge(int age) {
		this.startAge = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getStartAge() {
		return startAge;
	}

	public void setStartAge(int startAge) {
		this.startAge = startAge;
	}

	public int getEndAge() {
		return endAge;
	}

	public void setEndAge(int endAge) {
		this.endAge = endAge;
	}

	public String[] getEthnicGroups() {
		return ethnicGroups;
	}

	public void setEthnicGroups(String[] ethnicGroups) {
		this.ethnicGroups = ethnicGroups;
	}

	public String getSelectedStates() {
		return selectedStates;
	}

	public void setSelectedStates(String selectedStates) {
		this.selectedStates = selectedStates;
	}

	@Override
	public String toString() {
		return "SearchParam [searchBy=" + searchBy + ", searchText=" + searchText + ", startAge=" + startAge
				+ ", endAge=" + endAge + ", gender=" + gender + ", ethnicGroups=" + Arrays.toString(ethnicGroups)
				+ ", selectedStates=" + selectedStates + "]";
	}

}
