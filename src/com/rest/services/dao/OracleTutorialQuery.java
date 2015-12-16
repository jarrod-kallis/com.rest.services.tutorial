package com.rest.services.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.rest.services.classes.Employee;
import com.rest.services.classes.EmployeesOut;
import com.rest.services.classes.ResponseMessage;
import com.rest.services.utils.Converter;
import com.rest.services.utils.ToJson;

public class OracleTutorialQuery {

	public static String getDatabaseStatus() throws SQLException {
		Connection connection = null;
		PreparedStatement query = null;

		try {
			connection = OracleTutorialDb.getOracleTutorialDbConnection();
			query = connection
					.prepareStatement("select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') DATETIME from sys.dual");

			ResultSet rs = query.executeQuery();

			rs.next();

			return rs.getString("DATETIME");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (query != null) {
				query.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

		return "Server not responding";
	}

	public static Response getAllEmployees() throws SQLException {
		Connection connection = null;
		PreparedStatement query = null;

		try {
			connection = OracleTutorialDb.getOracleTutorialDbConnection();
			query = connection.prepareStatement("select * from EMP");

			ResultSet rs = query.executeQuery();

			JSONArray jsonArray = new ToJson().toJSONArray(rs);

			return Response.ok(jsonArray.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server wasn't able to retrieve employees").build();
		} finally {
			if (query != null) {
				query.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	public static Response getAllEmployeesByDepartmentName(String departmentName) throws SQLException {
		Connection connection = null;
		PreparedStatement query = null;
		// JSONArray jsonArray = new JSONArray();

		try {
			connection = OracleTutorialDb.getOracleTutorialDbConnection();
			query = connection.prepareStatement("select * from EMP emp join DEPT dept on dept.DEPTNO = emp.DEPTNO "
					+ "where UPPER(dept.DNAME) = ?");
			query.setString(1, departmentName.toUpperCase());

			ResultSet rs = query.executeQuery();

			EmployeesOut employees = new EmployeesOut();
			employees.employees = new ArrayList<Employee>();

			while (rs.next() == true) {
				employees.employees.add(Employee.map(rs));
			}

			employees.setSuccessful(true);
			employees.setMessage("Retrieved all employees for department: " + departmentName);

			// jsonArray = new ToJson().toJSONArray(rs);
			// jsonArray.join(new ResponseMessage(true, "Retrieved all
			// employees").toString());

			// return Response.ok(jsonArray.toString()).build();
			return Response.ok(employees).build();
		} catch (Exception e) {
			e.printStackTrace();
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setSuccessful(false);
			responseMessage.setMessage("Server wasn't able to retrieve employees for department: " + departmentName);

			return Response.status(500).entity(responseMessage).build();
		} finally {
			if (query != null) {
				query.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	public static Response insertEmployee(int id, String name, String job, int managerId, String hireDate, int salary,
			int commission, int departmentId) throws SQLException {
		Connection connection = null;
		PreparedStatement query = null;

		try {
			connection = OracleTutorialDb.getOracleTutorialDbConnection();
			query = connection.prepareStatement(
					"insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			query.setInt(1, id);
			query.setString(2, name);
			query.setString(3, job);
			query.setInt(4, managerId);
			query.setDate(5, Converter.convertStringToDate(hireDate, "yyyy-MM-dd"));
			query.setInt(6, salary);
			query.setInt(7, commission);
			query.setInt(8, departmentId);

			query.executeUpdate();

			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setSuccessful(true);
			responseMessage.setMessage("Insert successful");

			return Response.ok(responseMessage).build();
			// return 200;
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

		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setSuccessful(false);
		responseMessage.setMessage("Insert failed");

		return Response.status(500).entity("Insert failed").build();
		// return 500;
	}
}
