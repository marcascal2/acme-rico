package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "credit_cards")
public class CreditCard extends BaseEntity {

	@CreditCardNumber
	private String number;

	@Pattern(regexp = "[0-9]{2}/[0-9]{4}")
	@NotNull
	private String	deadline;

	@NotBlank
	@Pattern(regexp = "[0-9]{3}", message = "Incorrect CVV")
	private String cvv;

}
