package org.springframework.samples.acmerico.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cc_applications")
public class CreditCardApplication extends BaseEntity{
	
	@NotBlank
	@Pattern(regexp = "ACCEPTED|REJECTED|PENDING", message="Card application status only can be ACCEPTED, REJECTED or PENDING")
	private String status;
	
	@Valid
	@ManyToOne(optional = false)
	@NotNull
	private Client client;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private BankAccount bankAccount;

}
