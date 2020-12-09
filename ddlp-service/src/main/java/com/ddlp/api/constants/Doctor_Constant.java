package com.ddlp.api.constants;

public class Doctor_Constant {
	
	public static final String FETCH_SQL_BY_ID = "select * from doctor where doctor_id = :id";
	public static final String FETCH_SQL_BY_NAME = "select * from doctor where name = :name";
	public static final String FETCH_SQL = "select * from doctor";
	public static final String INSERT_SQL = "insert into doctor(address,contact_number,degree,designation,email_id,experience,name)"
			+ " values(:address,:contact_number,:degree,:designation, :email_id, :experience, :name)";
	public static final String UPDATE_SQL = "update doctor set address=:address, contact_number=:contact_number,degree=:degree,"
			+ "designation=:designation,email_id=:email_id,experience=:experience,name=:name where doctor_id=:id";
	public static final String DELETE_SQL = "delete from doctor where doctor_id=:id";
}
