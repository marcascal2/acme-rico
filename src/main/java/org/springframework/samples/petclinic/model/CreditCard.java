package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "credit_cards")
public class CreditCard extends BaseEntity {

	@CreditCardNumber
	private String number;

	@Pattern(regexp = "[0-9]{2}/[0-9]{4}", message = "Incorrect deadline")
	@NotNull
	private String deadline;

	@NotBlank
	@Pattern(regexp = "[0-9]{3}", message = "Incorrect CVV")
	private String cvv;
	
	//Relationships
//	@ManyToOne(optional = false)
//	private Client				employer;

}
