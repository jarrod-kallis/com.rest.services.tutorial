package com.rest.services.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rest.services.dao.OracleTutorialDb;

@Path("/v1/status")
public class V1_Status {

	private static final String api_version = "00.01.00";

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p>Java Web Service</p>";
	}

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version: " + api_version + "</p>";
	}

	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception {
		PreparedStatement query = null;
		Connection connection = null;

		try {
			connection = OracleTutorialDb.getOracleTutorialDbConnection().getConnection();
			query = connection
					.prepareStatement("select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') DATETIME from sys.dual");
			ResultSet rs = query.executeQuery();

			rs.next();

			return "<p>Database Status</p> <p>Database Date/Time: " + rs.getString("DATETIME") + "</p>";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (query != null) {
				query.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

		return null;
	}
}
