package org.springframework.samples.acmerico.model;

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorLoanAppTests {
	
	private static Loan loan = new Loan();
	private static BankAccount bankAccount = new BankAccount();
	private static Client client = new Client();
	private static User user = new User();
	
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
	static void populateLoan(){
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(700.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		loan.setClient(client);
	}
	
	@BeforeAll
	static void populateBankAccount(){
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);
	}
	
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {100.00, 100.01, 50000.00, 999999.99, 1000000.00})
	void positiveTestWithAmount(Double amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(amount);
		loanApp.setIncome(700.0);
		loanApp.setPurpose("This is a purpose");
		loanApp.setAmount_paid(1200.0);
		loanApp.setDestination(bankAccount);
		loanApp.setLoan(loan);
		loanApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		assertThat(constraintViolations.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {99.99, 1000000.01})
	void negativeTestWithAmount(Double amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(amount);
		loanApp.setIncome(700.0);
		loanApp.setPurpose("This is a purpose");
		loanApp.setAmount_paid(1200.0);
		loanApp.setDestination(bankAccount);
		loanApp.setLoan(loan);
		loanApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		if(amount < 100.00) {
			assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 100.00");
		}else {
			assertThat(violation.getMessage()).isEqualTo("must be less than or equal to 1000000.00");
		}
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {600.00, 600.01, 50000.00, 999999.99, 1000000.00})
	void positiveTestWithIncome(Double income) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(600.0);
		loanApp.setIncome(income);
		loanApp.setPurpose("This is a purpose");
		loanApp.setAmount_paid(1200.0);
		loanApp.setDestination(bankAccount);
		loanApp.setLoan(loan);
		loanApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		assertThat(constraintViolations.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {599.99, 1000000.01})
	void negativeTestWithIncome(Double income) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		LoanApplication loanApp = new LoanApplication();
		loanApp.setAmount(600.0);
		loanApp.setIncome(income);
		loanApp.setPurpose("This is a purpose");
		loanApp.setAmount_paid(1200.0);
		loanApp.setDestination(bankAccount);
		loanApp.setLoan(loan);
		loanApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		if(income < 600.00) {
			assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 600.00");
		}else {
			assertThat(violation.getMessage()).isEqualTo("must be less than or equal to 1000000.00");
		}
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
		loanApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
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
		loanApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
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
		loanApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
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
		loanApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenClientNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(900.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		loan.setClient(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

}