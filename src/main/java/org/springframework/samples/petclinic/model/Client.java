package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@Data
@MappedSuperclass
public class Client extends User {
	
	@Column(name = "addres")
	@NotEmpty
	private String addres;
	
	@Column(name = "birthdate")
	private LocalDate birthdate;
	
	@Column(name = "city")
	@NotEmpty
	private String city;

}
