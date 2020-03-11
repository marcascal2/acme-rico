package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
@Table(name = "transfer_applications")
public class TransferApplication extends BaseEntity {

	@NotEmpty
	@Pattern(regexp = "ACCEPTED|REJECTED|PENDING") // pending, accepted, rejected
	private String status;

	@DecimalMin(value = "0.0")
	private Double amount;

	@NotEmpty
	@Pattern(regexp = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}", message = "Invalid account number")
	private String account_number_destination;

	// Relationships
	@OneToOne(optional = true)
	private BankAccount account;
}
