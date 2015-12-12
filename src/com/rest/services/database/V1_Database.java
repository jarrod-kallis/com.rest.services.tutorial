package com.rest.services.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.rest.services.dao.OracleTutorialDb;
import com.rest.services.utils.ToJson;

@Path("/v1/database")
public class V1_Database {

	@Path("/employees")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployees() throws SQLException {
		PreparedStatement query = null;
		Connection connection = null;
		Response response = null;

		try {
			connection = OracleTutorialDb.getOracleTutorialDbConnection().getConnection();
			query = connection.prepareStatement("select * from EMP");
			ResultSet rs = query.executeQuery();

			JSONArray jsonArray = new ToJson().toJSONArray(rs);

			response = Response.ok(jsonArray.toString()).build();
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

		return response;
	}
}
