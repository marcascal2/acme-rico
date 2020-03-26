package org.springframework.samples.acmerico.model;

import java.time.LocalDate;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "clients")
public class Client extends Person {
	
	@Column(name = "address")
	@NotEmpty
	private String address;
	
	@Column(name = "birth_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate birthDate;
	
	@Column(name = "city")
	@NotEmpty
	private String city;
	
	@Column(name = "marital_status")
	@NotEmpty
	private String maritalStatus;
	
	@Column(name = "salary_per_year")
	@NotNull
	private Double salaryPerYear;
	
	@Column(name = "age")
	@NotNull
	private Integer age;
	
	@Column(name = "job")
	@NotEmpty
	private String job;
	
	@Column(name = "last_employ_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate lastEmployDate;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	
	@OneToMany(mappedBy = "client")
	private Collection<BankAccount> bankAccounts;
	
	@OneToMany(mappedBy = "client")
	public Collection<CreditCard> creditCards;
	
	@OneToMany(mappedBy = "client")
	public Collection<CreditCardApplication> creditCardApps;
	
	@OneToMany(mappedBy = "client")
	public Collection<InstantTransfer> instantTransfers;
	
	@OneToMany(mappedBy = "client")
	public Collection<TransferApplication> transferApps;
	
	@OneToMany(mappedBy = "client")
	public Collection<Loan> loans;
	
	@OneToMany(mappedBy = "client")
	public Collection<LoanApplication> loanApps;
}
