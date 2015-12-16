package com.rest.services.database;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.rest.services.classes.Employee;
import com.rest.services.dao.OracleTutorialQuery;

@Path("/v2/database")
public class V2_Database {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response defaultErrorMessage() {
		return Response.status(400)
				.entity("Error: please specify a department name for this search using URL: /database/employees/departments/{department-name}")
				.build();
	}

	@Path("/employees")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response employeesDefaultErrorMessage() {
		return defaultErrorMessage();
	}

	@Path("/employees/departments")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response departmentsDefaultErrorMessage() {
		return defaultErrorMessage();
	}

	@Path("/employees/departments/{department-name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeesByDepartmentName(@PathParam("department-name") String departmentName)
			throws SQLException {
		return new V1_Database().getEmployeesByDepartmentName(departmentName);
	}

	@Path("/employees")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertEmployee(String incomingEmployeeData)
			throws IOException, JsonParseException, JsonMappingException, SQLException {
		System.out.println("incomingEmployeeData: " + incomingEmployeeData);

		ObjectMapper mapper = new ObjectMapper();
		Employee employee = mapper.readValue(incomingEmployeeData, Employee.class);

		return OracleTutorialQuery.insertEmployee(employee.id, employee.name, employee.job, employee.managerId,
				employee.hireDate, employee.salary, employee.commission, employee.departmentId);
	}
}
