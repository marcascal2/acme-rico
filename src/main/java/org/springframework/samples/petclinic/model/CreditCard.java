package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "credit_cards")
public class CreditCard extends BaseEntity {

	@CreditCardNumber
	private String number;

	@Pattern(regexp = "[0-9]{2}/[0-9]{4}", message = "Incorrect deadline")
	private String	deadline;

	@Pattern(regexp = "[0-9]{3}", message = "Incorrect CVV")
	private String cvv;

}
