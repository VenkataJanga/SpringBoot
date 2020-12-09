package com.ddlp.api.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ddlp.api.dao.impl.DoctorDAOImpl;
import com.ddlp.api.model.Patient;


@Component
public class PatientRowMapper implements RowMapper<Patient> {
	
	@Autowired
	DoctorDAOImpl doctorDao;
	
	@Override
	public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
		Patient patient=new Patient();
		patient.setId(rs.getLong(1));
		patient.setName(rs.getString(2));
		patient.setAddress(rs.getString(3));
		patient.setGender(rs.getString(4));
		patient.setDisease(rs.getString(5));
		patient.setEmailId(rs.getString(6));
		patient.setContactNumber(rs.getString(7));
		patient.setDoctor(doctorDao.searchDoctorById(rs.getLong(8)));
		return patient;
		
		 
	}
}
