package com.hmsapp.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EnumType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsapp.entity.PatientEntity;
import com.hmsapp.model.LocationStats;
import com.hmsapp.model.SearchParam;
import com.hmsapp.service.LocationService;
import com.hmsapp.service.PatientService;

@Controller
public class HMSUIController {
	private static final Logger logger = LoggerFactory.getLogger(HMSUIController.class);

	@Autowired
	PatientService patientService;

	@Autowired
	Environment envtConfig;

	@Autowired
	LocationService locationService;
 

	@GetMapping("/")
	public String index(Model model) throws JsonProcessingException {
		SearchParam searchParam = new SearchParam();
		searchParam.setStartAge(5);
		searchParam.setEndAge(100);
		model.addAttribute("searchparam", searchParam);

		return "index";
	}

	@PostMapping("/submitSearch")
	public String submitSearch(Model model, @ModelAttribute("searchparam") SearchParam searchParam)
			throws IOException, GeneralSecurityException {
		logger.debug("inside method");
		logger.debug(searchParam.toString());

		long startTime = System.currentTimeMillis();
		List<PatientEntity> patientlist = patientService.searchPatientFromDB(searchParam);

		long elapsedTime = (System.currentTimeMillis() - startTime) ;

		logger.debug("sql query execution:" + elapsedTime);

		if (patientlist == null || patientlist.isEmpty()) {
			return "index";
		}
		model.addAttribute("patientlist", patientlist);

		// ------------- Ethnicity ----------------------
		startTime = System.currentTimeMillis();
		Map<String, List<PatientEntity>> ethnicityMap = patientlist.stream()
				.collect(Collectors.groupingBy(w -> w.getEthnicity()));

		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();

		for (Map.Entry<String, List<PatientEntity>> entry : ethnicityMap.entrySet()) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", entry.getKey());
			map.put("name", entry.getKey());

			map.put("y", (Double.valueOf(entry.getValue().size()) / Double.valueOf(patientlist.size())) * 100);
			list.add(map);
		}

		ObjectMapper obj = new ObjectMapper();
		String dataPoints = obj.writeValueAsString(list);

		model.addAttribute("dataEthnicity", dataPoints);

		elapsedTime = (System.currentTimeMillis() - startTime) ;

		logger.debug("ethnicity calculation:" + elapsedTime);

		// ----------------

		// ------------- Age Male----------------------
		startTime = System.currentTimeMillis();

		List<PatientEntity> patientlistMale = patientlist.stream().filter(p -> "Male".equalsIgnoreCase(p.getGender()))
				.collect(Collectors.toList());

		ObjectMapper obj1 = new ObjectMapper();

		int start = 0, addition = 5, end = 95;
		List<Map<Object, Object>> listMale = new ArrayList<Map<Object, Object>>();

		while (start <= end) {
			int to = start + addition - 1;
			int startFrom = start;
			long patientCountInRange = patientlistMale.stream().filter(p -> p.getAge() >= startFrom && p.getAge() <= to)
					.count();

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", String.valueOf(start) + " to " + String.valueOf(to));
			map.put("y", (Double.valueOf(patientCountInRange) / Double.valueOf(patientlist.size())) * 100);
			listMale.add(map);
			start = start + addition;
		}

		long patientCountInRange100PMale = patientlistMale.stream().filter(p -> p.getAge() >= 100).count();

		Map<Object, Object> map100PMale = new HashMap<Object, Object>();
		map100PMale.put("label", "100+");
		map100PMale.put("y", (Double.valueOf(patientCountInRange100PMale) / Double.valueOf(patientlist.size())) * 100);
		listMale.add(map100PMale);

		String dataPointsMale = obj1.writeValueAsString(listMale);

		model.addAttribute("dataMale", dataPointsMale);

		elapsedTime = (System.currentTimeMillis() - startTime) ;

		logger.debug("age male calculation:" + elapsedTime);

		// ------------- Age Female----------------------

		startTime = System.currentTimeMillis();

		List<PatientEntity> patientlistFemale = patientlist.stream()
				.filter(p -> "Female".equalsIgnoreCase(p.getGender())).collect(Collectors.toList());

		start = 0;
		addition = 5;
		end = 95;
		List<Map<Object, Object>> listFemale = new ArrayList<Map<Object, Object>>();

		while (start <= end) {
			int to = start + addition - 1;
			int startFrom = start;
			long patientCountInRange = patientlistFemale.stream()
					.filter(p -> p.getAge() >= startFrom && p.getAge() <= to).count();

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", String.valueOf(start) + " to " + String.valueOf(to));
			map.put("y", (Double.valueOf(patientCountInRange) / Double.valueOf(patientlist.size())) * 100);
			listFemale.add(map);
			start = start + addition;
		}

		long patientCountInRange100PFemale = patientlistFemale.stream().filter(p -> p.getAge() >= 100).count();

		Map<Object, Object> map100PFemale = new HashMap<Object, Object>();
		map100PFemale.put("label", "100+");
		map100PFemale.put("y",
				(Double.valueOf(patientCountInRange100PFemale) / Double.valueOf(patientlist.size())) * 100);
		listFemale.add(map100PFemale);

		obj1 = new ObjectMapper();

		model.addAttribute("dataFemale", obj1.writeValueAsString(listFemale));

		elapsedTime = (System.currentTimeMillis() - startTime) ;

		logger.debug("age female calculation:" + elapsedTime);

		// -------------Location data
		startTime = System.currentTimeMillis();
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

		elapsedTime = (System.currentTimeMillis() - startTime) ;

		logger.debug("location calculation:" + elapsedTime);

		return "success";

	}

	static <T> Collector<T, ?, List<T>> toSortedList(Comparator<? super T> c) {
		return Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), l -> {
			l.sort(c);
			return l;
		});
	}

	@GetMapping("/success")
	public String success(Model model) {
//		patientlist= patientService.searchPatientFromDB(searchParam);

//		model.addAttribute("patientlist",patientlist);

		return "success";
	}
}
