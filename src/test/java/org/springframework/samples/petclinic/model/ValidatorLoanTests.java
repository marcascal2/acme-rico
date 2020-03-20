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
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.Loan;
import org.springframework.samples.acmerico.model.User;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorLoanTests {
	
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
	
	@Test
	void shouldNotValidateWhenMinimunAmountDown100() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount(60.0);
		loan.setMinimum_income(700.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		loan.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 100.00");
		
	}
	
	@Test
	void shouldNotValidateWhenMinimunIncomeDown500() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(500.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		loan.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 600.00");
		
	}
	
	@Test
	void shouldNotValidateWhenNumberOfDeadlinesDown2() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(900.0);
		loan.setNumber_of_deadlines(1);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		loan.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 2");
		
	}
	
	@Test
	void shouldNotValidateWhenOpeningPriceNegative() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(900.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(-12.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		loan.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");
		
	}
	
	@Test
	void shouldNotValidateWhenMonthlyFeeDown001() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(900.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.0);
		loan.setSingle_loan(true);
		loan.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0.01");
		
	}
	
	@Test
	void shouldNotValidateWhenSinglesLoanNull() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(900.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(null);
		loan.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}

}
