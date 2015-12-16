package com.rest.services.database;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.rest.services.dao.OracleTutorialQuery;

@Path("/v3/database")
public class V3_Database {

	@Path("/employees")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertEmployee(String incomingEmployeeData)
			throws IOException, JsonParseException, JsonMappingException, SQLException, JSONException {
		System.out.println("incomingEmployeeData: " + incomingEmployeeData);

		JSONObject jsonEmployeeObj = new JSONObject(incomingEmployeeData);
		System.out.println("jsonEmployeeObj: " + jsonEmployeeObj.toString());

		Response response = OracleTutorialQuery.insertEmployee(jsonEmployeeObj.optInt("id"),
				jsonEmployeeObj.optString("name"), jsonEmployeeObj.optString("job"),
				jsonEmployeeObj.optInt("managerId"), jsonEmployeeObj.optString("hireDate"),
				jsonEmployeeObj.optInt("salary"), jsonEmployeeObj.optInt("commission"),
				jsonEmployeeObj.optInt("departmentId"));

		return response;
		// if (httpCode == 200) {
		// ResponseMessage responseMessage = new ResponseMessage();
		// responseMessage.setSuccessful(true);
		// responseMessage.setMessage("Employee inserted");
		// // JSONObject jsonResult = new JSONObject();
		// // jsonResult.put("HTTP_CODE", 200);
		// // jsonResult.put("MSG", "Employee inserted");
		// //
		// // return Response.ok(new
		// // JSONArray().put(jsonResult).toString()).build();
		// return Response.ok(responseMessage).build();
		// } else {
		// return Response.serverError().build();
		// }
	}
}
