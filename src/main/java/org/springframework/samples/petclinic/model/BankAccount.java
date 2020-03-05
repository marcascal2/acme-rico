package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bank_accounts")
@Valid
public class BankAccount extends BaseEntity {
	
	@Pattern(regexp = "^[A-Z]{2}(?:[ ]?[0-9]){18,20}$", message="Invalid account number")
	private String accountNumber;
	
	private Double amount;
	
	@Past
	private LocalDate createdAt;
	
	@Length(max = 30)
	private String alias;
	
}
