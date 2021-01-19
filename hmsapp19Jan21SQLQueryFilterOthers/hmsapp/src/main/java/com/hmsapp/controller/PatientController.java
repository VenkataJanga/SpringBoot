package com.hmsapp.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hmsapp.entity.PatientEntity;
import com.hmsapp.model.PatientResponse;
import com.hmsapp.model.SearchParam;
import com.hmsapp.service.PatientDBService;
import com.hmsapp.service.PatientService;

@RestController
public class PatientController {
	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	PatientService patientService;

	@Autowired
	PatientDBService patientDBService;

	@PostMapping("/searchPatientsBySearchParam")
	public List<PatientEntity> searchPatients(@RequestBody SearchParam searchParam) {
		return patientService.searchPatientFromDB(searchParam);

	}

	/**
	 * This API will search patients based on input param for Age , Ethnicity and
	 * Map
	 * 
	 * @param searchParam
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@PostMapping("/searchPatients")
	public PatientResponse submitSearch(@RequestBody SearchParam searchParam)
			throws IOException, GeneralSecurityException {
		logger.debug("inside method");
		logger.debug(searchParam.toString());

		return patientService.submitSearch(searchParam);

	}


}
