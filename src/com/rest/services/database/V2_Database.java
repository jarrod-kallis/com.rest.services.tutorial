package com.rest.services.database;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rest.services.dao.OracleTutorialQuery;

@Path("/v2/database")
public class V2_Database {

	@Path("/employees")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployees(@QueryParam("department-name") String departmentName) throws SQLException {
		if (departmentName == null) {
			return Response.status(500).entity("Please specify a department name for this search").build();
		}

		Response response = OracleTutorialQuery.getAllEmployeesByDepartmentName(departmentName);

		return response;
	}
}
