package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;

import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
@Table(name = "transfers")
public class Transfer extends BaseEntity {

	@NotEmpty
	@Pattern(regexp = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}", message = "Invalid account number")
	private String accountNumber;

	@DecimalMin(value = "100.00")
	private Double amount;

	@NotEmpty
	@Pattern(regexp = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}", message = "Invalid account number")
	private String destination;

}
