package com.hmsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hmsapp.entity.PatientEntity;
import com.hmsapp.model.SearchParam;
import com.hmsapp.service.PatientService;


@RestController
public class PatientController {
	@Autowired
	PatientService patientService;
	
	@PostMapping("/searchPatientsBySearchParam")
	public  List<PatientEntity> searchPatients(@RequestBody SearchParam searchParam) {
		return patientService.searchPatientFromDB(searchParam);
		
	}
}
