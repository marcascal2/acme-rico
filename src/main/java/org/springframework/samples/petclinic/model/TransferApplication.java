package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "transfer_applications")
public class TransferApplication extends BaseEntity {

	@NotEmpty
	@Pattern(regexp = "ACCEPTED|REJECTED|PENDING", message="Transfer application status only can be ACCEPTED, REJECTED or PENDING") //pending, accepted, rejected
	private String status;
	
	@Range(min = 100,message = "The amount must be greater than € 100,"
			+ " please if you need a smaller amount make an instant transfer")
	private Double amount;

	@NotEmpty
	@Pattern(regexp = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}", message = "Invalid account number")
	private String account_number_destination;
}
