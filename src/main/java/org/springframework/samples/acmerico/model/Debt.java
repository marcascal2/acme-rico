package org.springframework.samples.acmerico.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "debts")
public class Debt {

    @DecimalMin(value = "0")
	@NotBlank
	private Double amount;

	@NotBlank
	@Pattern(regexp = "[0-9]{2}/[0-9]{4}", message = "Incorrect refresh date")
    private String	refreshDate;
    
    @Valid
	@OneToOne(cascade = CascadeType.ALL)
	@NotNull
	private LoanApplication loanApplication;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private Client client;

}