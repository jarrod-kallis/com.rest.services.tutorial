package com.rest.services.status;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rest.services.dao.OracleTutorialQuery;

@Path("/v1/status")
public class V1_Status {

	private static final String api_version = "00.01.00";

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getTitle() {
		return "<p>Java Web Service</p>";
	}

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getVersion() {
		return "<p>Version: " + api_version + "</p>";
	}

	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getDatabaseStatus() throws SQLException {
		String dateTime = OracleTutorialQuery.getDatabaseStatus();

		return "<p>Database Status</p> <p>Database Date/Time: " + dateTime + "</p>";
	}
}
