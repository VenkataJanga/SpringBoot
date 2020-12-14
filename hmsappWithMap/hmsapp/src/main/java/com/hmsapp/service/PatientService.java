package com.hmsapp.service;

import java.sql.Date;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmsapp.entity.PatientEntity;
import com.hmsapp.model.LocationStats;
import com.hmsapp.model.SearchParam;
import com.hmsapp.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	LocationService locationService;

	public List<PatientEntity> searchPatientFromDB(SearchParam searchParam) {
		List<PatientEntity> patientListResponse = new ArrayList<PatientEntity>();
		if (searchParam != null && searchParam.getGender() != null && !searchParam.getGender().isEmpty()) {
			patientListResponse = patientRepository.findByGenderAgeBetween(searchParam.getGender(),
					searchParam.getStartAge(), searchParam.getEndAge());
		} else {
			patientListResponse = patientRepository.findByAgeBetween(searchParam.getStartAge(),
					searchParam.getEndAge());
		}

		return patientListResponse;
	}

	/*
	 * public void bulkupload() {
	 * 
	 * List<PatientEntity> patientListResponse = new ArrayList<PatientEntity>();
	 * String[] ethnicityR = { "Non-minority-White", "Asian",
	 * "Black-Africa Descent", "Black- Carribbean Descen", "Black- North American",
	 * "Latin American", "Middle Eastern", "Indigenous" };
	 * 
	 * // String[] ethnicityR = { "Asian", "Latin American", "Indigenous" };
	 * String[] raceR = { "African", "American", "Oceanian", "Asian" }; String[]
	 * genderR = { "Male", "Female" }; List<LocationStats> postCodesMaster = new
	 * ArrayList<LocationStats>( locationService.getAllStats().values() );
	 * 
	 * 
	 * for (int i = 0; i < 10000; i++) { String patientid = "p4" + i; String
	 * firstName = "fname" + i; String lastName = "lname" + i; String middleName =
	 * "mname" + i; String fullName = firstName + middleName + lastName; String
	 * primaryPhoneNumber = String.valueOf(123456789 + i); String primaryEmail =
	 * fullName + "@gmail.com"; String homeAddressLine1 = "addr1" + i; String
	 * homeAddressLine2 = "addr2" + i; String homeCity = "citi" + i; String
	 * homeState = "state" + i;
	 * 
	 * String ethnicity = (ethnicityR[new Random().nextInt(ethnicityR.length)]);
	 * String gender = (genderR[new Random().nextInt(genderR.length)]); String race
	 * = (raceR[new Random().nextInt(raceR.length)]);
	 * 
	 * locationService.getAllStats().size(); String homePostalCode =
	 * postCodesMaster.get(new
	 * Random().nextInt(locationService.getAllStats().size())).getZipCode();
	 * 
	 * // Random birthdate java.sql.Date birthDate; Random rnd; long ms;
	 * 
	 * // Get a new random instance, seeded from the clock rnd = new Random();
	 * 
	 * // Get an Epoch value roughly between 1940 and 2010 // -946771200000L =
	 * January 1, 1940 // Add up to 70 years to it (using modulus on the next long)
	 * ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 *
	 * 1000));
	 * 
	 * // Construct a date birthDate = new Date(ms); // Random Birthdate
	 * 
	 * PatientEntity objPatientEntity = new PatientEntity(patientid, "web",
	 * lastName, primaryPhoneNumber, primaryEmail, homeAddressLine1,
	 * homeAddressLine2, homeCity, homeState, homePostalCode, fullName, firstName,
	 * middleName, race, ethnicity, gender, birthDate);
	 * 
	 * patientListResponse.add(objPatientEntity);
	 * 
	 * }
	 * 
	 * patientRepository.saveAll(patientListResponse);
	 * 
	 * }
	 */

	public List<PatientEntity> getAlldetails() {
		
		return patientRepository.findAll();
	}

}
