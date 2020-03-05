package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client extends Person {
	
	@Column(name = "address")
	@NotEmpty
	private String address;
	
	@Column(name = "birth_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthDate;
	
	@Column(name = "city")
	@NotEmpty
	private String city;
	
	@Column(name = "marital_status")
	@NotNull
	private String maritalStatus;
	
	@Column(name = "salary_per_year")
	@NotNull
	private Double salaryPerYear;
	
	@Column(name = "age")
	@NotNull
	private Integer age;
	
	@Column(name = "job")
	@NotNull
	private String job;
	
	@Column(name = "last_employ_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate lastEmployDate;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

}
