package com.hmsapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmsapp.entity.PatientEntity;
import com.hmsapp.model.SearchParam;
import com.hmsapp.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	PatientRepository patientRepository;

	public List<PatientEntity> searchPatientFromDB(SearchParam searchParam) {
		List<PatientEntity> patientListResponse = new ArrayList<PatientEntity>();
		patientListResponse = patientRepository.findByAgeBetween(searchParam.getGender(), searchParam.getStartAge(),
				searchParam.getEndAge());
		return patientListResponse;
	}

}
