package org.springframework.samples.acmerico.model;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "loans")
public class Loan extends BaseEntity {
	
	@NotBlank
	private String description;

	@DecimalMin(value = "100.00")
	@DecimalMax(value = "1000000.00")
	@NotNull
	private Double minimum_amount;
	
	@DecimalMin(value = "600.00")
	@DecimalMax(value = "1000000.00")
	@NotNull
	private Double minimum_income;
	
	@Min(value = 2)
	@NotNull
	private Integer number_of_deadlines;
	
	@Min(value = 0)
	@NotNull
	private Double opening_price;
	
	@DecimalMin(value = "0.01")
	@NotNull
	private Double monthly_fee;
	
	@NotNull
	private Boolean single_loan;
	
	@OneToMany(mappedBy = "loan")
	@ToString.Exclude
	private Collection<LoanApplication> loanApplications;
} 
