package com.capipyre.cis.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContext;

public class DbConnector {

	private static Connection conn;

	public DbConnector() {
	}

	public Statement createStmt() {
		try {
			return DbConnector.conn.createStatement();
		} catch (SQLException e) {
			return null;
		}
	}

	public PreparedStatement createPreparedStmt(String sql) {
		try {
			return DbConnector.conn.prepareStatement(sql);
		} catch (SQLException e) {
			return null;
		}
	}

	public static void initDbConnector(ServletContext sc) {
		InputStream is = sc.getResourceAsStream("/WEB-INF/application.properties");
		Properties prop = new Properties();
		try {
			prop.load(is);

			Class.forName("oracle.jdbc.OracleDriver");

			String jdbcUrl = prop.getProperty("config.datasource.url");
			String jdbcUsername = prop.getProperty("config.datasource.username");
			String jdbcPassword = prop.getProperty("config.datasource.password");

			DbConnector.initConnection(jdbcUrl, jdbcUsername, jdbcPassword);

			System.out.println("Successfully connected to \"" + jdbcUrl + "\"");
		} catch (IOException e) {
			System.out.println("\"src/webapp/WEB-INF/application.properties\" does not exist");
		} catch (SQLException e) {
			System.out.println(
					"Cannot connect to database. Check if \"application.properties\" has the following properties:");
			System.out.println("    - config.datasource.url");
			System.out.println("    - config.datasource.username");
			System.out.println("    - config.datasource.password");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("oracle.jdbc.driver.OracleDriver is not found");
		}
	}

	public static void initConnection(String jdbcUrl, String username, String password) throws SQLException {
		DbConnector.conn = DriverManager.getConnection(jdbcUrl, username, password);
	}

	public static DbConnector getInstance() {
		return new DbConnector();
	}

}
