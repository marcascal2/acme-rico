package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cc_applications")
public class CreditCardApplication extends BaseEntity{
	
	@NotBlank
	private String status;
	
	@Valid
	@ManyToOne(optional = false)
	@NotNull
	private Client client;
	
	@Valid
	@ManyToOne(optional = false)
	@NotNull
	private BankAccount bankAccount;

}
