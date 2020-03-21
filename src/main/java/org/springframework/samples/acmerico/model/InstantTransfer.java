package org.springframework.samples.acmerico.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "instant_transfers")
public class InstantTransfer extends BaseEntity {

	@DecimalMin(value = "0.01")
	@DecimalMax(value = "100.00")
	@NotNull
	private Double amount;
	
	@NotEmpty
	@Pattern(regexp = "^[A-Z]{2}(?:[ ]?[0-9]){18,20}$", message = "Invalid account number")
	private String destination;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private Client client;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private BankAccount bankAccount;
}
