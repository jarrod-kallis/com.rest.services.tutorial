package com.rest.services.classes;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	public int id;
	public String name;
	public String job;
	public int managerId;
	public String hireDate;
	public int salary;
	public int commission;
	public int departmentId;

	public static Employee map(ResultSet rs) throws SQLException {
		Employee employee = new Employee();

		employee.id = rs.getInt("EMPNO");
		employee.name = rs.getString("ENAME");
		employee.job = rs.getString("JOB");
		employee.managerId = rs.getInt("MGR");
		employee.hireDate = rs.getString("HIREDATE");
		employee.salary = rs.getInt("SAL");
		employee.commission = rs.getInt("COMM");
		employee.departmentId = rs.getInt("DEPTNO");

		return employee;
	}
}
