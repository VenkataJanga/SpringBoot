package com.hmsapp.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsapp.constants.Constants;
import com.hmsapp.entity.PatientEntity;
import com.hmsapp.model.AgeResponse;
import com.hmsapp.model.LocationStats;
import com.hmsapp.model.PatientResponse;
import com.hmsapp.model.SearchParam;
import com.hmsapp.repository.PatientRepository;

@Service
@CacheConfig(cacheNames={"patients"})   

public class PatientService {
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);


	@Autowired
	LocationService locationService;

	@Autowired
	PatientDBService patientDBService;
	
	
	/**
	 * This method will fetch data from DB for input search params
	 * 
	 * @param searchParam
	 * @return
	 */
	public List<PatientEntity> searchPatientFromDB(SearchParam searchParam) {

		long startTime = System.currentTimeMillis();

		List<PatientEntity> allPatientsList = patientDBService.fetchPatientList(searchParam );

		long elapsedTime = (System.currentTimeMillis() - startTime);
		logger.debug("sql query execution in searchPatientFromDB:" + elapsedTime);
		
		
		logger.debug("Total patient count:" + allPatientsList.size());

		return allPatientsList;
	}

	/**
	 * This method will return true if string present in array as a elements
	 * @param inputStr
	 * @param items
	 * @return
	 */
	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
	    return Arrays.stream(items).anyMatch(inputStr::equals);
	}
	/**
	 * This will return chart data for Male and Feaale
	 * 
	 * @param gender
	 * @param patientlist
	 * @return
	 * @throws JsonProcessingException
	 */
	public List<AgeResponse>  getChartDetailsForGender(String gender, List<PatientEntity> patientlist)
			throws JsonProcessingException {
		List<AgeResponse> ageDistributionList= new ArrayList<AgeResponse>();
		
		// It will filter patient list based on gender like Male or Female
		List<PatientEntity> patientlistForGender = patientlist.stream()
				.filter(p -> gender.equalsIgnoreCase(p.getGender())).collect(Collectors.toList());

		ObjectMapper obj1 = new ObjectMapper();

		/**
		 * This will form a group like 0-4,5-9 and calculate percentage of that group
		 */

		int start = 0, addition = 5, end = 95;

		while (start <= end) {
			int to = start + addition - 1;
			int startFrom = start;
			long patientCountInRange = patientlistForGender.stream()
					.filter(p -> p.getAge() >= startFrom && p.getAge() <= to).count();

			
			ageDistributionList.add(new AgeResponse(startFrom+"-"+to, getAgePercentage(patientCountInRange, patientlist.size(), gender)));
			start = start + addition;
		}

		long patientCount100Plus = patientlistForGender.stream().filter(p -> p.getAge() >= 100).count();
		String ageCategory100Plus="100 +";


		ageDistributionList.add(new AgeResponse(ageCategory100Plus, getAgePercentage(patientCount100Plus, patientlist.size(), gender)));



		return ageDistributionList;

	}
	
	/**
	 * This method will calculate the percentage
	 * @param patientCountinRange
	 * @param totalSize
	 * @param gender
	 * @return
	 */
	private Double getAgePercentage(long patientCountinRange,int  totalSize,String gender) {
		Double value =round((Double.valueOf(patientCountinRange) / Double.valueOf(totalSize)) * 100, 1) ;
 			return (value);
	}

	private static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	/**
	 * This method search the data based on input search
	 * 
	 * @param model
	 * @param searchParam
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public PatientResponse submitSearch( SearchParam searchParam) throws IOException, GeneralSecurityException {
		logger.debug("inside method");
		logger.debug(searchParam.toString());
		long startTimeTotal = System.currentTimeMillis();

		PatientResponse patientResponse = new PatientResponse();
		
		ObjectMapper obj = new ObjectMapper();

		long startTime = System.currentTimeMillis();

		// This will call method and fetch patient list from DB
		List<PatientEntity> patientlist = searchPatientFromDB(searchParam);

		long elapsedTime = (System.currentTimeMillis() - startTime);

		logger.debug("sql query execution:" + elapsedTime);
		if(patientlist!=null && !patientlist.isEmpty()) {
			patientResponse.setPatientPresentFlag("VALID");
		}else {
			patientResponse.setPatientPresentFlag(null);

		}
		
		// ------------- Ethnicity ----------------------
		/*
		 * This will group patient list by Ethnicity and calcuate percentage of each
		 * group
		 */
		startTime = System.currentTimeMillis();
		Map<String, List<PatientEntity>> ethnicityMap = patientlist.stream()
				.collect(Collectors.groupingBy(w -> w.getEthnicity()));

		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();

		for (Map.Entry<String, List<PatientEntity>> entry : ethnicityMap.entrySet()) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(Constants.NAME, entry.getKey());

//			map.put(Constants.Y, (Double.valueOf(entry.getValue().size()) / Double.valueOf(patientlist.size())) * 100);
			map.put(Constants.Y, Double.valueOf(entry.getValue().size() ));

			list.add(map);
		}

		String dataPoints = obj.writeValueAsString(list);

		patientResponse.setDataEthnicity(dataPoints);
		
		elapsedTime = (System.currentTimeMillis() - startTime);

		logger.debug("ethnicity calculation:" + elapsedTime);

		// ----------------

		boolean populateMaleChartData=false;
		boolean populateFemaleChartData=false;

		if(searchParam.getGender() == null || searchParam.getGender().isEmpty()){
			populateMaleChartData=true;
			populateFemaleChartData=true;
		}else if( searchParam.getGender() != null && !searchParam.getGender().isEmpty()&&
				Constants.MALE.equalsIgnoreCase(searchParam.getGender()) ) {
			populateMaleChartData=true;
		}else if( searchParam.getGender() != null && !searchParam.getGender().isEmpty()&&
				Constants.FEMALE.equalsIgnoreCase(searchParam.getGender()) ) {
			populateFemaleChartData=true;
		}
		// ------------- Age Male----------------------
		if(populateMaleChartData) {
			startTime = System.currentTimeMillis();
			// Call a method getChartDetailsForGender and get chart details for Male
	
			 List<AgeResponse> dataAgeMale=getChartDetailsForGender(Constants.MALE, patientlist);
			patientResponse.setDataAgeMale(dataAgeMale);
			
			elapsedTime = (System.currentTimeMillis() - startTime);
	
			logger.debug("age male calculation:" + elapsedTime);
		}
		// ------------- Age Female----------------------
		if(populateFemaleChartData) {
			startTime = System.currentTimeMillis();

			// Call a method getChartDetailsForGender and get chart details for Female
			 List<AgeResponse> dataAgeFemale=getChartDetailsForGender(Constants.FEMALE, patientlist);

			patientResponse.setDataAgeFemale(dataAgeFemale);
			
			elapsedTime = (System.currentTimeMillis() - startTime);

			logger.debug("age female calculation:" + elapsedTime);

			// -------------Location data
			startTime = System.currentTimeMillis();

		}

		/**
		 * This will update latitude and longitude for all patiens from the loaded list
		 */
		Map<String, LocationStats> locationStats = locationService.getAllStats();
		if (locationStats != null && !locationStats.isEmpty()) {
			for (PatientEntity patient : patientlist) {
				if (patient.getHomePostalCode() != null && !patient.getHomePostalCode().isEmpty()) {
					if (locationStats.containsKey(patient.getHomePostalCode())) {
						LocationStats location = locationStats.get(patient.getHomePostalCode());

						patient.setLatitude(location.getLatitude());
						patient.setLongitude(location.getLongitude());
					}
				}
			}
			patientResponse.setLocationStatsList(patientlist);
		}

		elapsedTime = (System.currentTimeMillis() - startTime);

		logger.debug("location calculation:" + elapsedTime);

		long elapsedTimeTotal = (System.currentTimeMillis() - startTimeTotal);

		logger.debug("Total time required for submitSearch:" + elapsedTimeTotal);

		return patientResponse;
	}


}
