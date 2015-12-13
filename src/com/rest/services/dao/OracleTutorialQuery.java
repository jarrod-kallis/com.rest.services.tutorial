package com.rest.services.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

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
		JSONArray jsonArray = new JSONArray();

		try {
			connection = OracleTutorialDb.getOracleTutorialDbConnection();
			query = connection.prepareStatement("select * from EMP emp join DEPT dept on dept.DEPTNO = emp.DEPTNO "
					+ "where UPPER(dept.DNAME) = ?");
			query.setString(1, departmentName.toUpperCase());

			ResultSet rs = query.executeQuery();

			jsonArray = new ToJson().toJSONArray(rs);

			return Response.ok(jsonArray.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server wasn't able to retrieve employees for department: " + departmentName).build();
		} finally {
			if (query != null) {
				query.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}
}
