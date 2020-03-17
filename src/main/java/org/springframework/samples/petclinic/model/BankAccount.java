package org.springframework.samples.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
	
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{2}(?:[ ]?[0-9]){18,22}$", message="Invalid account number")
	@NotNull
	private String accountNumber;
	
	@NotNull
	private Double amount;
	
	
	@NotNull
	@Past
	private LocalDateTime createdAt;
	
	@Length(max = 30)
	private String alias;
	
	@Valid
	@ManyToOne()
	@NotNull
	private Client client;
	
}
