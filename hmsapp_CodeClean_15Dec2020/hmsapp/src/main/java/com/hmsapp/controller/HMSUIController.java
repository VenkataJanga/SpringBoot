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
import com.hmsapp.constants.Constants;
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

	/**
	 * This API will show home page/ index page 
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/")
	public String index(Model model) throws JsonProcessingException {
		//It will initialize page with start age as 0 and end age as 130
		SearchParam searchParam = new SearchParam();
		searchParam.setStartAge(0);
		searchParam.setEndAge(130);
		model.addAttribute(Constants.SEARCHPARAM, searchParam);

		return Constants.INDEX;
	}

	/**
	 * This API will search patients based on input param and also add chart data for Age , Ethnicity and Map
	 * @param model
	 * @param searchParam
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@PostMapping("/searchPatients")
	public String submitSearch(Model model, @ModelAttribute(Constants.SEARCHPARAM) SearchParam searchParam)
			throws IOException, GeneralSecurityException {
		logger.debug("inside method");
		logger.debug(searchParam.toString());
		return patientService.submitSearch(model, searchParam);

	}
}
