package com.rest.services.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class OracleTutorialDb {

	private static DataSource oracleTutorialDb = null;
	private static Context context = null;

	public static DataSource getOracleTutorialDbConnection() throws Exception {

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
}
