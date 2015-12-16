package com.rest.services.classes;

public class EmployeeOut extends ResponseMessage {
	private static final long serialVersionUID = 1L;

	public Employee employee;

	public EmployeeOut() {
		employee = new Employee();
	}
}
