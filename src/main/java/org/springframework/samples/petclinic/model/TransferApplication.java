package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "transfer_applications")
public class TransferApplication extends BaseEntity {

	@NotEmpty
	private TransferApplicationStatus status;

}
