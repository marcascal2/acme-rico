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
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "loan_applications")
public class LoanApplication extends BaseEntity {

	//Debe ser mayor que minimun_amount
	@DecimalMin(value = "100.00")
	@DecimalMax(value = "1000000.00")
	private Double amount;
	
	@NotBlank
	private String purpose;
	
	@NotBlank
	@Pattern(regexp = "ACCEPTED|REJECTED|PENDING", message="Loan application status only can be ACCEPTED, REJECTED or PENDING")
	private String status;
	
	@NotNull
	private Double amount_paid;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private BankAccount bankAccount;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private Loan loan;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private Client client;
	
	//PROPIEDADES DERIVADAS
	
	public Double getAmountToPay() {
		return (getAmount() - getLoan().getOpening_price()) * (getLoan().getMonthly_fee()*(Math.pow(1+getLoan().getMonthly_fee(), getLoan().getNumber_of_deadlines())))/(Math.pow(1+getLoan().getMonthly_fee(), getLoan().getNumber_of_deadlines())-1) ;
	}
	
	public boolean isPaid() {
		return getAmountToPay()*getLoan().getNumber_of_deadlines()==getAmount_paid();
	}
}
