package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cc_applications")
public class CreditCardApplication extends BaseEntity{
	
	@NotBlank
	@NotNull
	private String status;

}

enum StatusCreditCardApplication{
	ACCEPTED,
	REJECTED,
	PENDING
	
}
