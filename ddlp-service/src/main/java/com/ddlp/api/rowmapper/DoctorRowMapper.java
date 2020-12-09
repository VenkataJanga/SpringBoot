package com.ddlp.api.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ddlp.api.model.Doctor;

@Component
public class DoctorRowMapper implements RowMapper<Doctor> {

	@Override
	public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
		Doctor doctor = new Doctor();
		doctor.setId(rs.getLong(1));
		doctor.setName(rs.getString(2));
		doctor.setDesignation(rs.getString(3));
		doctor.setDegree(rs.getString(4));
		doctor.setExperience(rs.getInt(5));
		doctor.setAddress(rs.getString(6));
		doctor.setEmailId(rs.getString(7));	
		doctor.setContactNumber(rs.getString(8));

		return doctor;
	}

}
