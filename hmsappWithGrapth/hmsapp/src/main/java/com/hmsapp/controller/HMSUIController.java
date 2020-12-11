package com.hmsapp.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EnumType;

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
import com.hmsapp.model.SearchParam;
import com.hmsapp.service.PatientService;

@Controller
public class HMSUIController {
	@Autowired
	PatientService patientService;
	List<PatientEntity> patientlist;

	@Autowired
	Environment envtConfig;

	@GetMapping("/")
	public String index(Model model) throws JsonProcessingException {
		SearchParam searchParam = new SearchParam();
		model.addAttribute("searchparam", searchParam);

		// -------------------
//		
//		Map<Object, Object> map = null;
//		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
//
//		map = new HashMap<Object, Object>();
//		map.put("label", "Non-minority White");
//		map.put("y", 40);
//		list.add(map);
//		map = new HashMap<Object, Object>();
//		map.put("label", "Asian");
//		map.put("y", 14);
//		list.add(map);
//		map = new HashMap<Object, Object>();
//		map.put("label", "Black-Africa Descent");
//		map.put("y", 4);
//		list.add(map);
//		map = new HashMap<Object, Object>();
//		map.put("label", "Black-Caribbean Descent");
//		map.put("y", 5);
//		list.add(map);
//		map = new HashMap<Object, Object>();
//		map.put("label", "Black- North American");
//		map.put("y", 17);
//		list.add(map);
//		map = new HashMap<Object, Object>();
//		map.put("label", "Latien American");
//		map.put("y", 9);
//		
//		map = new HashMap<Object, Object>();
//		map.put("label", "Middle Eastern");
//		map.put("y", 10);
//		
//		map = new HashMap<Object, Object>();
//		map.put("label", "Indigenous");
//		map.put("y", 1);
//		list.add(map);
//		
//		ObjectMapper obj =new ObjectMapper();
//		String dataPoints = obj.writeValueAsString(list);
//		
//		model.addAttribute("dataPoints", dataPoints);

		// -------------------
		return "index";
	}

	@PostMapping("/submitSearch")
	public String submitSearch(Model model, @ModelAttribute("searchparam") SearchParam searchParam)
			throws IOException, GeneralSecurityException {
		System.out.println("inside method");
		System.out.println(searchParam.getGender() + searchParam.getAge());

		patientlist = patientService.searchPatientFromDB(searchParam);

		model.addAttribute("patientlist", patientlist);

		// ------------- Ethnicity ----------------------
		Map<String, List<PatientEntity>> ethnicityMap = patientlist.stream()
				.collect(Collectors.groupingBy(w -> w.getEthnicity()));

		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();

		for (Map.Entry<String, List<PatientEntity>> entry : ethnicityMap.entrySet()) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", entry.getKey());
			map.put("y", (Double.valueOf(entry.getValue().size()) / Double.valueOf(patientlist.size())) * 100);
			list.add(map);
		}

		ObjectMapper obj = new ObjectMapper();
		String dataPoints = obj.writeValueAsString(list);

		model.addAttribute("dataEthnicity", dataPoints);

		// ----------------

		// ------------- Age ----------------------
		// -----------------Male
		List<PatientEntity> patientlistMale = patientlist.stream()
				.filter(p -> "Male".equalsIgnoreCase(p.getGender()))
//				.sorted(Comparator.comparingInt(PatientEntity::getAge))
				.collect(Collectors.toList());
		
		
		Map<Object, List<PatientEntity>> ageMap = patientlistMale.stream()
				.collect(Collectors.groupingBy (w -> getAgeGroup(w)) );

		List<Map<Object, Object>> listMale = new ArrayList<Map<Object, Object>>();

		for (Map.Entry<Object, List<PatientEntity>> entry : ageMap.entrySet()) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", getLabel(AgeGroup.valueOf(String.valueOf(entry.getKey()) ) ));
			map.put("y", (Double.valueOf(entry.getValue().size()) / Double.valueOf(patientlist.size())) * 100);
			listMale.add(map);
		}

		ObjectMapper obj1 = new ObjectMapper();
		String dataPointsMale = obj1.writeValueAsString(listMale);

		model.addAttribute("dataMale", dataPointsMale);

		// -----------------Female
		List<PatientEntity> patientlistFemale = patientlist.stream().filter(p -> "Female".equalsIgnoreCase(p.getGender()))
				.collect(Collectors.toList());

		Map<Object, List<PatientEntity>> ageMapFemale = patientlistFemale.stream()
				.collect(Collectors.groupingBy(w -> getAgeGroup(w)));

		List<Map<Object, Object>> listFemale = new ArrayList<Map<Object, Object>>();

		for (Map.Entry<Object, List<PatientEntity>> entry : ageMapFemale.entrySet()) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", getLabel(AgeGroup.valueOf(String.valueOf(entry.getKey()) ) ) );
			map.put("y", (Double.valueOf(entry.getValue().size()) / Double.valueOf(patientlist.size())) * 100);
			listFemale.add(map);
		}

		ObjectMapper obj2 = new ObjectMapper();
		String dataPointsFemale = obj2.writeValueAsString(listFemale);

		model.addAttribute("dataFemale", dataPointsFemale);

		// ----------------

		return "success";

	}
	
	static <T> Collector<T,?,List<T>> toSortedList(Comparator<? super T> c) {
	    return Collectors.collectingAndThen(
	        Collectors.toCollection(ArrayList::new), l->{ l.sort(c); return l; } );
	}

	private String getLabel(AgeGroup ageGroup) {
		
		switch(ageGroup){
		   case ZEROS:
	           	return "0-9";
	         case TENS:
	        	return "10-19";
	         case TWENTIES:
		        	return "20-29";
	         case THIRTIES:
		        	return "30-39";
	         case FORTIES:
		        	return "40-49";
	         case FIFTIES:
		        	return "50-59";
	         case SIXTIES:
		        	return "60-69";
	         case SEVENTIES:
		        	return "70-79";
	         case EIGHTIES:
		        	return "80-89";
	         case NINTIES:
		        	return "90-99";
	         case HUNDREDS:
		        	return "90+";
		        	
	         default:
	        	 return "default";
		}
	}
	private AgeGroup getAgeGroup(PatientEntity p) {
		if (p.getAge() >= 0 && p.getAge() < 10)
			return AgeGroup.ZEROS;
		if (p.getAge() >= 10 && p.getAge() < 20)
			return AgeGroup.TENS;
		if (p.getAge() >= 20 && p.getAge() < 30)
			return AgeGroup.TWENTIES;
		if (p.getAge() >= 30 && p.getAge() < 40)
			return AgeGroup.THIRTIES;
		if (p.getAge() >= 40 && p.getAge() < 50)
			return AgeGroup.FORTIES;
		if (p.getAge() >= 50 && p.getAge() < 60)
			return AgeGroup.FIFTIES;
		if (p.getAge() >= 60 && p.getAge() < 70)
			return AgeGroup.SIXTIES;
		if (p.getAge() >= 70 && p.getAge() < 80)
			return AgeGroup.SEVENTIES;
		if (p.getAge() >= 80 && p.getAge() < 90)
			return AgeGroup.EIGHTIES;
		if (p.getAge() >= 90 && p.getAge() < 100)
			return AgeGroup.NINTIES;
		if (p.getAge() >100)
			return AgeGroup.HUNDREDS;
return AgeGroup.HUNDREDS;
	}

	enum AgeGroup {
		ZEROS, TENS, TWENTIES, THIRTIES, FORTIES, FIFTIES, SIXTIES, SEVENTIES, EIGHTIES, NINTIES, HUNDREDS;
	}

	@GetMapping("/success")
	public String success(Model model) {
//		patientlist= patientService.searchPatientFromDB(searchParam);

//		model.addAttribute("patientlist",patientlist);

		return "success";
	}
}
