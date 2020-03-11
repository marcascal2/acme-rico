package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "credit_cards")
public class CreditCard extends BaseEntity {

	@CreditCardNumber
	private String number;

	@Pattern(regexp = "[0-9]{2}/[0-9]{4}", message = "Incorrect deadline")
	private String	deadline;

	@Pattern(regexp = "[0-9]{3}", message = "Incorrect CVV")
	private String cvv;
	
	@Valid
	@ManyToOne()
	@NotNull
	private Client client;

}
