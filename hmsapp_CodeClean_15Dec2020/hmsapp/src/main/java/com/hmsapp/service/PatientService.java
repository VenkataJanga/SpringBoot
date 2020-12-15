package com.hmsapp.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsapp.constants.Constants;
import com.hmsapp.entity.PatientEntity;
import com.hmsapp.model.LocationStats;
import com.hmsapp.model.SearchParam;
import com.hmsapp.repository.PatientRepository;

@Service
public class PatientService {
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	LocationService locationService;

	/**
	 * This method will fetch data from DB for input search params
	 * 
	 * @param searchParam
	 * @return
	 */
	public List<PatientEntity> searchPatientFromDB(SearchParam searchParam) {
		List<PatientEntity> patientListResponse = new ArrayList<PatientEntity>();
		// If Gender is not empty then search with gender and age else search with only
		// age
		if (searchParam != null && searchParam.getGender() != null && !searchParam.getGender().isEmpty()) {
			patientListResponse = patientRepository.findByGenderAgeBetween(searchParam.getGender(),
					searchParam.getStartAge(), searchParam.getEndAge());
		} else {
			patientListResponse = patientRepository.findByAgeBetween(searchParam.getStartAge(),
					searchParam.getEndAge());
		}

		return patientListResponse;
	}

	/**
	 * This will return chart data for Male and Feaale
	 * 
	 * @param gender
	 * @param patientlist
	 * @return
	 * @throws JsonProcessingException
	 */
	public String getChartDetailsForGender(String gender, List<PatientEntity> patientlist)
			throws JsonProcessingException {

		// It will filter patient list based on gender like Male or Female
		List<PatientEntity> patientlistForGender = patientlist.stream()
				.filter(p -> gender.equalsIgnoreCase(p.getGender())).collect(Collectors.toList());

		ObjectMapper obj1 = new ObjectMapper();

		/**
		 * This will form a group like 0-4,5-9 and calculate percentage of that group
		 */

		int start = 0, addition = 5, end = 130;
		List<Map<Object, Object>> listGender = new ArrayList<Map<Object, Object>>();

		while (start <= end) {
			int to = start + addition - 1;
			int startFrom = start;
			long patientCountInRange = patientlistForGender.stream()
					.filter(p -> p.getAge() >= startFrom && p.getAge() <= to).count();

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(Constants.LABEL, String.valueOf(start) + " to " + String.valueOf(to));
			map.put(Constants.Y, (Double.valueOf(patientCountInRange) / Double.valueOf(patientlist.size())) * 100);
			listGender.add(map);
			start = start + addition;
		}


		return obj1.writeValueAsString(listGender);

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
	public String submitSearch(Model model, SearchParam searchParam) throws IOException, GeneralSecurityException {
		logger.debug("inside method");
		logger.debug(searchParam.toString());

		ObjectMapper obj = new ObjectMapper();

		long startTime = System.currentTimeMillis();

		// This will call method and fetch patient list from DB
		List<PatientEntity> patientlist = searchPatientFromDB(searchParam);

		long elapsedTime = (System.currentTimeMillis() - startTime);

		logger.debug("sql query execution:" + elapsedTime);

		if (patientlist == null || patientlist.isEmpty()) {
			return "index";
		}
		model.addAttribute("patientlist", patientlist);

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

			map.put(Constants.Y, (Double.valueOf(entry.getValue().size()) / Double.valueOf(patientlist.size())) * 100);
			list.add(map);
		}

		String dataPoints = obj.writeValueAsString(list);

		model.addAttribute("dataEthnicity", dataPoints);

		elapsedTime = (System.currentTimeMillis() - startTime);

		logger.debug("ethnicity calculation:" + elapsedTime);

		// ----------------

		// ------------- Age Male----------------------
		startTime = System.currentTimeMillis();

		// Call a method getChartDetailsForGender and get chart details for Male
		String dataPointsMale = getChartDetailsForGender(Constants.MALE, patientlist);

		model.addAttribute("dataMale", dataPointsMale);

		elapsedTime = (System.currentTimeMillis() - startTime);

		logger.debug("age male calculation:" + elapsedTime);

		// ------------- Age Female----------------------

		startTime = System.currentTimeMillis();

		// Call a method getChartDetailsForGender and get chart details for Female
		String dataPointsFemale = getChartDetailsForGender(Constants.FEMALE, patientlist);

		model.addAttribute("dataFemale", dataPointsFemale);

		elapsedTime = (System.currentTimeMillis() - startTime);

		logger.debug("age female calculation:" + elapsedTime);

		// -------------Location data
		startTime = System.currentTimeMillis();

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
			model.addAttribute("locationStats", patientlist);

		}

		elapsedTime = (System.currentTimeMillis() - startTime);

		logger.debug("location calculation:" + elapsedTime);

		return Constants.SUCCESS;

	}

}
