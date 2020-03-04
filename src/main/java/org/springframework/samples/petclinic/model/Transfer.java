package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "transfers")
public class Transfer extends BaseEntity {

	@NotEmpty
	@Pattern(regexp = "^[A-Z]{2}(?:[]?[0-9]){18-20}$", message = "Invalid account number")
	private String accountNumber;

	@NotNull
	private Double amount;

	@NotEmpty
	@Pattern(regexp = "^[A-Z]{2}(?:[]?[0-9]){18-20}$", message = "Invalid account number")
	private String destination;

}
