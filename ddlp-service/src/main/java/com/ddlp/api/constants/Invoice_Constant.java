package com.ddlp.api.constants;

public class Invoice_Constant {
	public static final String FETCH_SQL_BY_ID = "select * from invoice where id = :id";
	public static final String FETCH_SQL = "select * from invoice";
	public static final String INSERT_SQL = "insert into invoice( patientId,doctorId,billId,totalAmount)"
			+ " values(:patientId, :doctorId, :billId, :totalAmount)";
	public static final String UPDATE_SQL = "update invoice set patientId=:patientId, doctorId=:doctorId, "
			+ "billId=:billId, totalAmount=:totalAmount where id=:id";
	public static final String DELETE_SQL = "delete from invoice where id=:id";
}
