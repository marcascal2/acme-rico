package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import org.springframework.samples.petclinic.utilities.EmployeeType;

@MappedSuperclass
public class Employee extends User{

	@Column(name = "salary")
	@NotNull
	private Double salary;
	
	@Column(name = "employee_type")
	@NotNull
	private EmployeeType employeeType;

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}
	
}
