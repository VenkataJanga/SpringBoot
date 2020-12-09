package com.ddlp.api.constants;

public class Bill_Constant {
		public static final String FETCH_SQL_BY_ID = "select * from bill where bill_id = :id";
		public static final String FETCH_SQL = "select * from bill";
		public static final String INSERT_SQL = "insert into bill( particular,quantity,unitValue)"
				+ " values(:particular, :quantity, :unitValue)";
		public static final String UPDATE_SQL = "update bill set particular=:particular, quantity=:quantity, "
				+ "unitValue=:unitValue where bill_id=:id";
		public static final String DELETE_SQL = "delete from bill where bill_id=:id";

}
