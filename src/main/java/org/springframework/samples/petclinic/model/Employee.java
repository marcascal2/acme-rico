package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.samples.petclinic.utilities.EmployeeType;

import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee extends Person {
	
	@NotNull
	private Double salary;
	
	@NotNull
	private EmployeeType employeeType;
	
}
