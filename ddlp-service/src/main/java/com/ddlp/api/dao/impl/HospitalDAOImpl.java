package com.ddlp.api.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ddlp.api.constants.Hospital_Constant;
import com.ddlp.api.dao.HospitalDAO;
import com.ddlp.api.model.Hospital;
import com.ddlp.api.rowmapper.HospitalRowMapper;



@Repository
public class HospitalDAOImpl implements HospitalDAO {
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	HospitalRowMapper hospitalRowMapper;
	@Override
	public List<Hospital> getHospital() {
		return namedParameterJdbcTemplate.query(Hospital_Constant.FETCH_SQL, hospitalRowMapper);
	}
}
