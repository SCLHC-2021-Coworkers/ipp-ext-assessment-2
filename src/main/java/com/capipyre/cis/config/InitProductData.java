package com.capipyre.cis.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.capipyre.cis.util.DbConnector;

public class InitProductData implements ApplicationInitializer {

	@Override
	public void init() {
		String tableExistsSql = new StringBuilder()
				.append("select count(*) ")
				.append("from user_tables ut ")
				.append("where ut.table_name = 'PRODUCT_TBL'")
				.toString();

		String dropTableSql = "drop table PRODUCT_TBL";

		String createTableSql = new StringBuilder()
				.append("create table PRODUCT_TBL ( ")
					.append("prod_code char(4) not null, ")
					.append("manufacturer varchar2(10) not null, ")
					.append("created_at date default sysdate, ")
					.append("primary key (prod_code) ")
				.append(")")
				.toString();

		String insertInitDataSql = new StringBuilder()
				.append("insert all ")
					.append("into PRODUCT_TBL (prod_code, manufacturer) ")
					.append("values ('A100', '»ï¼ºÀüÀÚ') ")
				.append("select * from dual")
				.toString();

		Statement stmt = null;
		try {
			stmt = DbConnector.getInstance().createStmt();

			ResultSet rs = stmt.executeQuery(tableExistsSql);

			rs.next();
			boolean tableExists = rs.getInt(1) > 0;

			if (tableExists) {
				stmt.executeUpdate(dropTableSql);
				stmt.executeUpdate(createTableSql);
				stmt.executeUpdate(insertInitDataSql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
