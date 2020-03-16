package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "cc_applications")
public class CreditCardApplication extends BaseEntity{
	
	@NotBlank
	private String status;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@NotNull
	private Client client;

}
