package com.hmsapp.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hmsapp.constants.Constants;
import com.hmsapp.entity.PatientEntity;
import com.hmsapp.model.EthnicGroup;
import com.hmsapp.model.PatientResponse;
import com.hmsapp.model.SearchParam;
import com.hmsapp.service.PatientDBService;
import com.hmsapp.service.PatientService;

@Controller
public class HMSUIController {
	private static final Logger logger = LoggerFactory.getLogger(HMSUIController.class);

	@Autowired
	PatientService patientService;

	@Autowired
	Environment envtConfig;

	@Autowired
	PatientDBService patientDBService;

	@Value("#{${filter.race}}")  
	private Map<String, String> race;

	/**
	 * This API will show home page/ index page
	 * 
	 * @param model
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	@GetMapping("/")
	public String index(Model model) throws IOException, GeneralSecurityException {
		// It will initialize page with start age as 0 and end age as 130
		SearchParam searchParam = new SearchParam();
		searchParam.setStartAge(0);
		searchParam.setEndAge(130);
		
		String [] defaultrace= {Constants.ASIAN};
		searchParam.setEthnicGroups(defaultrace);
		model.addAttribute(Constants.SEARCHPARAM, searchParam);

		int[] rangeVal = { 0, 130 };
		model.addAttribute("rangeVal", rangeVal);

		searchParam.setSearchBy(Constants.FIND_ALL);
		PatientResponse patientResponse = new PatientResponse();

		model.addAttribute("patientPresentFlag", patientResponse.getPatientPresentFlag());

		model.addAttribute("dataEthnicity", patientResponse.getDataEthnicity());
		model.addAttribute("dataMale", patientResponse.getDataAgeMale());
		model.addAttribute("dataFemale", patientResponse.getDataAgeFemale());
		model.addAttribute("locationStats", patientResponse.getLocationStatsList());
	
		populateRaceFilter(model);
		return Constants.INDEX;
	}

	/**
	 * This method will populate the race/ethnicity filter
	 * @param model
	 */
	private void populateRaceFilter(Model model) {
		List<EthnicGroup> ethnicCheckList= new ArrayList<EthnicGroup>();

		for (Map.Entry<String, String> entry : race.entrySet()) {
			ethnicCheckList.add(new EthnicGroup(entry.getValue(), entry.getKey()));

		}
		model.addAttribute("allRaceGroups", ethnicCheckList);
		
	}
	@GetMapping("/bulkupload")
	public void bulkupload() throws IOException, GeneralSecurityException {
		logger.debug("inside method");
		patientDBService.bulkupload();

	}

	@PostMapping("/submitSearch")
	public String submitSearch(Model model, @ModelAttribute(Constants.SEARCHPARAM) SearchParam searchParam)
			throws IOException, GeneralSecurityException {
		logger.debug("inside method");
		logger.debug(searchParam.toString());

		model.addAttribute(Constants.SEARCHPARAM, searchParam);

		int[] rangeVal = { searchParam.getStartAge(), searchParam.getEndAge() };
		model.addAttribute("rangeVal", rangeVal);

		PatientResponse patientResponse = patientService.submitSearch(searchParam);

		model.addAttribute("patientPresentFlag", patientResponse.getPatientPresentFlag());

		model.addAttribute("dataEthnicity", patientResponse.getDataEthnicity());
		model.addAttribute("dataMale", patientResponse.getDataAgeMale());
		model.addAttribute("dataFemale", patientResponse.getDataAgeFemale());
		model.addAttribute("locationStats", patientResponse.getLocationStatsList());

		populateRaceFilter(model);

		return Constants.INDEX;
	}
}
