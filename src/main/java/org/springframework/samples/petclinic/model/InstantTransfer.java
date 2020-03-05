package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "instant_transfers")
@Valid
public class InstantTransfer extends BaseEntity {

	@DecimalMin(value = "0.01")
	@DecimalMax(value = "200.00")
	private Double amount;
	
	@NotEmpty
	@Pattern(regexp = "^[A-Z]{2}(?:[ ]?[0-9]){18,20}$")
	private String destination;
}
