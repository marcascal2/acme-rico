package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "transfers")
public class Transfer extends BaseEntity {

	@NotEmpty
	@Pattern(regexp = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}", message = "Invalid account number")
	private String accountNumber;

	@Range(min = 100,message = "The amount must be greater than € 100,"
			+ " please if you need a smaller amount make an instant transfer")
	private Double amount;

	@NotEmpty
	@Pattern(regexp = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}", message = "Invalid account number")
	private String destination;

}
