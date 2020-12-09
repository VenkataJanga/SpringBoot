package com.hmsui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsui.entity.Patient;
import com.hmsui.model.SearchParam;
import com.hmsui.repository.PatientRepository;

@Service
public class PatientsService {
	@Autowired
	RestTemplate resttemlate;

	@Autowired
	Environment envtConfig;

	@Autowired
	PatientRepository patientRepository;

	public String searchPatients(SearchParam searchParam) throws JsonProcessingException {
		List<Patient> patientresponse =searchPatientFromDB(searchParam);
		 ObjectMapper mapper = new ObjectMapper();
		String response= mapper.writeValueAsString(patientresponse);
		return response;
		
		
	}

	public List<Patient> searchPatientFromDB(SearchParam searchParam) {
		List<Patient> patientListResponse = new ArrayList<Patient>();
		if ("Name".equalsIgnoreCase(searchParam.getSearchBy())) {
			patientListResponse = patientRepository.findByNameLike("%" + searchParam.getSearchText() + "%");

		} else if ("Disease".equalsIgnoreCase(searchParam.getSearchBy())) {
			patientListResponse = patientRepository.findByDiseaseLike("%" + searchParam.getSearchText() + "%");

		} else if ("ContactNumber".equalsIgnoreCase(searchParam.getSearchBy())) {
			patientListResponse = patientRepository.findByContactNumberLike("%" + searchParam.getSearchText() + "%");
		} else if ("AgeGender".equalsIgnoreCase(searchParam.getSearchBy())) {
			patientListResponse = patientRepository.findByAgeBetween(searchParam.getGender(),searchParam.getStartAge(),searchParam.getEndAge());
		}
		
		
		
		
		return patientListResponse;
	}

}
