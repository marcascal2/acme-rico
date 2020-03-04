package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "transfer_applications")
public class TransferApplication extends BaseEntity {

	@NotNull
	private Double amount;
	
	@NotEmpty
	private TransferApplicationStatus status;

	@NotEmpty
	@Pattern(regexp = "^[A-Z]{2}(?:[]?[0-9]){18-20}$", message = "Invalid account number")
	private String accountNumber;
}
