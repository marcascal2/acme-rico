package org.springframework.samples.petclinic.model;

import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BaseEntity {
	
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{2}(?:[ ]?[0-9]){18,22}$", message="Invalid account number")
	@NotNull
	private String accountNumber;
	
	@NotNull
	private Double amount;
	
	
	@NotNull
	@Past
	private LocalDateTime createdAt;
	
	@Length(max = 30)
	private String alias;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private Client client;
	
	@OneToMany(mappedBy = "bankAccount")
	private Collection<CreditCardApplication> creditCardApps;
	
	@OneToMany(mappedBy = "bankAccount")
	private Collection<CreditCard> creditCards;
	
	@OneToMany(mappedBy = "bankAccount")
	private Collection<InstantTransfer> instantTransfers;
	
	@OneToMany(mappedBy = "bankAccount")
	private Collection<TransferApplication> transfersApps;
	
}
