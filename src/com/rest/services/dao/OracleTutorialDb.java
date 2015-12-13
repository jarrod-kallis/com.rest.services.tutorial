package com.rest.services.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class OracleTutorialDb {

	private static DataSource oracleTutorialDb = null;
	private static Context context = null;

	private static DataSource getOracleTutorialDb() throws Exception {

		try {
			if (context == null) {
				Context initialContext = new InitialContext();
				context = (Context) initialContext.lookup("java:/comp/env");
			}

			if (oracleTutorialDb == null) {
				oracleTutorialDb = (DataSource) context.lookup("jdbc/tutorialdb");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return oracleTutorialDb;
	}

	protected static Connection getOracleTutorialDbConnection() {
		Connection connection = null;

		try {
			connection = getOracleTutorialDb().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

}
