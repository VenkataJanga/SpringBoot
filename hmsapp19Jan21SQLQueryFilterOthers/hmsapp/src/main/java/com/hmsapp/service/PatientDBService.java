package com.hmsapp.service;
 
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.hmsapp.constants.Constants;
import com.hmsapp.entity.PatientEntity;
import com.hmsapp.model.EthnicGroup;
import com.hmsapp.model.LocationStats;
import com.hmsapp.model.SearchParam;
import com.hmsapp.repository.PatientRepository;

@Service
public class PatientDBService {
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	LocationService locationService;


	@Value("#{${filter.race.others}}")  
	private Map<String, String> raceOthers;

	/**
	 * This will return all the patients from DB 
	 * This method made cacheable to cache 
	 * @return
	 */
	public List<PatientEntity> fetchPatientList(SearchParam searchParam) {
		logger.debug("inside method fetchAllPatients()");
		String gender =(searchParam.getGender() != null && !searchParam.getGender().isEmpty())? searchParam.getGender():null;
		List<String> states=null;
		
		if(searchParam.getSelectedStates() != null && !searchParam.getSelectedStates().isEmpty()) {
			states = Arrays.asList(searchParam.getSelectedStates().split(","));
		}
		List<String> raceList=new ArrayList<String>();

		if(searchParam.getEthnicGroups()!=null && searchParam.getEthnicGroups().length>0){
			List<String>	selectedRaceList=Arrays.asList(searchParam.getEthnicGroups());
			for(String race: selectedRaceList) {
				raceList.add(race);
			}
		}else {
			raceList=null;
		}

		if(raceList!=null ) {
			if(raceList.contains(Constants.OTHERS)) {
				
				List<EthnicGroup> otherRaceList = getOtherRaceFilter();
				if(otherRaceList!=null && !otherRaceList.isEmpty()) {
					for(EthnicGroup otherRace: otherRaceList) {
						raceList.add(otherRace.getId());
					}
				}
				raceList.remove(Constants.OTHERS);
			}
		}
		  
	
		
		List<PatientEntity> patientListResponse = new ArrayList<PatientEntity>();
		 patientListResponse = patientRepository.findPatientsBySearchParam(gender, searchParam.getStartAge(), searchParam.getEndAge(),states,raceList);
		
		return patientListResponse;
	}
 
	public void bulkupload() {

		List<PatientEntity> patientListResponse = new ArrayList<PatientEntity>();
		String[] raceR = { "AMERICAN INDIAN/ ALASKA NATIVE", "AFRICAN AMERICAN (BLACK)", "Hispanic or Latino or Spanish Origin", "WHITE","LAOTIAN","FILIPINO","VIETNAMESE","THAI","CAMBODIAN","CHINESE"};
		String[] genderR = { "Male", "Female" };
		List<LocationStats> postCodesMaster = new ArrayList<LocationStats>(locationService.getAllStats().values());

		for (int i = 0; i < 10000; i++) {
			String patientid = "p1" + i;
			String firstName = "fname" + i;
			String lastName = "lname" + i;
			String middleName = "mname" + i;
			String fullName = firstName + middleName + lastName;
			String primaryPhoneNumber = String.valueOf(123456789 + i);
			String primaryEmail = fullName + "@gmail.com";
			String homeAddressLine1 = "addr1" + i;
			String homeAddressLine2 = "addr2" + i;
			String homeCity = "citi" + i;
			//String homeState = "state" + i;
			String race= raceR[new Random().nextInt(raceR.length)];
			String ethnicity = race;
			String gender = (genderR[new Random().nextInt(genderR.length)]);

			//locationService.getAllStats().size();
			int postalCodeIndex=new Random().nextInt(locationService.getAllStats().size());
			
			String homePostalCode = postCodesMaster.get(postalCodeIndex).getZipCode();

			String homeState = postCodesMaster.get(postalCodeIndex).getState();
			
			// Random birthdate
			java.sql.Date birthDate;
			Random rnd;
			long ms;

			// Get a new random instance, seeded from the clock
			rnd = new Random();

			// Get an Epoch value roughly between 1940 and 2010
			// -946771200000L = January 1, 1940
			// Add up to 70 years to it (using modulus on the next long)
			ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));

			// Construct a date
			birthDate = new Date(ms);
			// Random Birthdate

			PatientEntity objPatientEntity = new PatientEntity(patientid, "web", lastName, primaryPhoneNumber,
					primaryEmail, homeAddressLine1, homeAddressLine2, homeCity, homeState, homePostalCode, fullName,
					firstName, middleName, race, ethnicity, gender, birthDate);

			patientListResponse.add(objPatientEntity);

		}

		patientRepository.saveAll(patientListResponse);

	}
	
	/**
	 * This method will return all other race lsit
	 * @return
	 */
	private List<EthnicGroup>  getOtherRaceFilter() {
		List<EthnicGroup> otherRaceList= new ArrayList<EthnicGroup>();

		for (Map.Entry<String, String> entry : raceOthers.entrySet()) {
			otherRaceList.add(new EthnicGroup(entry.getValue(), entry.getKey()));

		}
		return otherRaceList;
	}
}
