package org.springframework.samples.acmerico.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "loan_applications")
public class LoanApplication extends BaseEntity {

	@DecimalMin(value = "100.00")
	@DecimalMax(value = "1000000.00")
	private Double amount;
	
	@DecimalMin(value = "600.00")
	@DecimalMax(value = "1000000.00")
	private Double income;
	
	@NotBlank
	private String purpose;
	
	@NotNull
	private Double amount_paid;
	
	@Valid
	@ManyToOne
	@NotNull
	private BankAccount destination;
	
	@Valid
	@ManyToOne
	@NotNull
	private Loan loan;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private Client client;
	
	//PROPIEDADES DERIVADAS
	
	public Double getAmountToPay() {
		return (getAmount() - getLoan().getOpening_price()) / getLoan().getNumber_of_deadlines() * 100 * getLoan().getMonthly_fee();
	}
}
