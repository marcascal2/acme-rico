package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorLoanAppTests {
	
	private static Loan loan = new Loan();
	private static BankAccount bankAccount = new BankAccount();
	private static Client client = new Client();
	private static User user = new User();
	
	@BeforeAll
	static void populateLoan(){
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(700.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
	}
	
	@BeforeAll
	static void populateUser() {
		user.setUsername("userPrueba");
		user.setPassword("userPrueba");
		user.setEnabled(true);
	}
	
	@BeforeAll
	static void populateClient(){
		client.setFirstName("Germán");
		client.setLastName("Márquez Trujillo");
		client.setAddress("C/ Marques de Aracena, 37");
		client.setBirthDate(LocalDate.parse("1998-04-15"));
		client.setCity("Sevilla");
		client.setMaritalStatus("Single");
		client.setSalaryPerYear(2000.0);
		client.setAge(21);
		client.setJob("DP2 Developement Student");
		client.setLastEmployDate(LocalDate.parse("2019-04-15"));
		client.setUser(user);
		client.setBankAccounts(new ArrayList<BankAccount>());
		client.getBankAccounts().add(bankAccount);
	}
	
	@BeforeAll
	static void populateBankAccount(){
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("GERMANOCTAKO");
		bankAccount.setClient(client);
	}
	
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenMinimunAmountDown100() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(60.0);
		loanApp.setIncome(700.0);
		loanApp.setPurpose("This is a purpose");
		loanApp.setAmount_paid(1200.0);
		loanApp.setDestination(bankAccount);
		loanApp.setLoan(loan);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("amount");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 100.00");
		
	}
	
	@Test
	void shouldNotValidateWhenMinimunIncomeDown500() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(1500.0);
		loanApp.setIncome(400.0);
		loanApp.setPurpose("This is a purpose");
		loanApp.setAmount_paid(1200.0);
		loanApp.setDestination(bankAccount);
		loanApp.setLoan(loan);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("income");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 600.00");
		
	}
	
	@Test
	void shouldNotValidateWhenPurposeBlank() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(1500.0);
		loanApp.setIncome(700.0);
		loanApp.setPurpose("");
		loanApp.setAmount_paid(1200.0);
		loanApp.setDestination(bankAccount);
		loanApp.setLoan(loan);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("purpose");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
		
	}
	
	@Test
	void shouldNotValidateWhenAmountPaidNull() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(1500.0);
		loanApp.setIncome(700.0);
		loanApp.setPurpose("This is a purpose");
		loanApp.setAmount_paid(null);
		loanApp.setDestination(bankAccount);
		loanApp.setLoan(loan);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("amount_paid");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}
	
	@Test
	void shouldNotValidateWhenDestinationNull() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(1500.0);
		loanApp.setIncome(700.0);
		loanApp.setPurpose("This is a purpose");
		loanApp.setAmount_paid(1200.0);
		loanApp.setDestination(null);
		loanApp.setLoan(loan);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("destination");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}
	
	@Test
	void shouldNotValidateWhenLoanNull() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(1500.0);
		loanApp.setIncome(700.0);
		loanApp.setPurpose("This is a purpose");
		loanApp.setAmount_paid(1200.0);
		loanApp.setDestination(bankAccount);
		loanApp.setLoan(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("loan");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}

}
