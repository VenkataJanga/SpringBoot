package com.ddlp.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ddlp.api.model.Doctor;
import com.ddlp.api.model.Hospital;
import com.ddlp.api.model.Invoice;
import com.ddlp.api.model.Patient;
import com.ddlp.api.service.DdlpService;




@RestController
@RequestMapping("/hms")
public class DdlpController {

	

	
	@Autowired
	DdlpService service;
	
	@GetMapping("/")
	String home() {
		return "<h1>Welcome to Hospital Management System</h1>";
	}
	
	//Hospital
	
	@GetMapping("/hospital")
	List<Hospital> getHospital(){
		return service.getHospital();
	}
	
	//Doctors
	
	@GetMapping("/doctors")
	List<Doctor> showAllDoctors(){
		List<Doctor> listAllDoctors= new ArrayList<>();
		listAllDoctors=service.showAllDoctors();
		return listAllDoctors;
	}
	
	@GetMapping("/doctors/viewByName/{name}")
	List<Map<String, Object>> doctorViewByName(@PathVariable("name") String name ) {
		return service.searchDoctorByName(name);
	}
	
	@GetMapping("/doctors/viewById/{id}")
	Doctor doctorViewById(@PathVariable("id") long id) {
		return service.searchDoctorById(id);
	}
	
	@PostMapping("/doctors/addDoctor")
	@ResponseStatus(value = HttpStatus.OK)
	void addDoctor(@RequestBody Doctor doctor) throws Exception{
		service.createDoctor(doctor);
	}
	
	@PutMapping("/doctors/updateDoctor/{id}")
	@ResponseStatus(value=HttpStatus.OK)
	void updateDoctor(@PathVariable("id") long id, @RequestBody Doctor doctor) throws Exception{
		doctor.setId(id);
		service.updateDoctor(doctor);
	}
	
	@DeleteMapping ("/doctors/deleteDoctor/{id}")
	@ResponseStatus(value=HttpStatus.OK)
	void deleteDoctor(@PathVariable("id") long id)throws Exception{
		service.deleteDoctor(id);
	}
	//Patients
	
	@GetMapping("/patients")
	List<Patient> showAllPatients(){
		List<Patient> listAllPatients= new ArrayList<Patient>();
		listAllPatients=service.showAllPatients();
		return listAllPatients;
	}
	
	@GetMapping("/patients/viewByName/{name}")
	List<Map<String, Object>> patientViewByName(@PathVariable("name") String name ) {
		return service.searchPatientByName(name);
	}
	
	@GetMapping("/patients/viewById/{id}")
	Patient patientViewById(@PathVariable("id") long id) {
		return service.searchPatientById(id);
	}
	
	@PostMapping("/patients/addPatient")
	@ResponseStatus(value = HttpStatus.OK)
	void addPatient(@RequestBody Patient patient) throws Exception{
		service.createPatient(patient);
	}
	
	@PutMapping("/patients/updatePatient/{id}")
	@ResponseStatus(value=HttpStatus.OK)
	void updatePatient(@PathVariable("id") long id, @RequestBody Patient patient) throws Exception{
		patient.setId(id);
		service.updatePatient(patient);
	}
	
	@DeleteMapping("/patients/deletePatient/{id}")
	@ResponseStatus(value=HttpStatus.OK)
	void deletePatient(@PathVariable("id") long id)throws Exception{
		service.deletePatient(id);
	}
	//Invoice 
	
	@GetMapping("/invoice")
	List<Invoice> showinvoice(){
		return service.showInvoice();
	}
	

}
