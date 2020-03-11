package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "cc_applications")
public class CreditCardApplication extends BaseEntity{
	
	@NotBlank
	private String status;

}

//enum StatusCreditCardApplication{
//	ACCEPTED,
//	REJECTED,
//	PENDING
//	
//}
