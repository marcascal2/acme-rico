package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import org.springframework.samples.petclinic.utilities.EmployeeType;

@Data
@MappedSuperclass
public class Employee extends User{

	@Column(name = "salary")
	@NotNull
	private Double salary;
	
	@Column(name = "employee_type")
	@NotNull
	private EmployeeType employeeType;
	
}
