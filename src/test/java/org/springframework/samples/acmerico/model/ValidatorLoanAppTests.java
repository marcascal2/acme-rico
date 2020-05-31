package org.springframework.samples.acmerico.model;

import static org.springframework.samples.acmerico.util.ViolationAssertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
	private static LoanApplication loanApp = new LoanApplication();

	@BeforeAll
	static void populateUser() {
		user.setUsername("userPrueba");
		user.setPassword("userPrueba");
		user.setEnabled(true);
	}

	@BeforeAll
	static void populateClient() {
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
	static void populateLoan() {
		loan.setDescription("This is a loan");
		loan.setMinimum_amount(1550.0);
		loan.setMinimum_income(700.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
	}

	@BeforeAll
	static void populateBankAccount() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);
	}

	@BeforeEach
	private void resetLoanApp() {
		loanApp.setAmount(50000.);
		loanApp.setPurpose("This is a purpose");
		loanApp.setStatus("PENDING");
		loanApp.setAmount_paid(1200.0);
		loanApp.setBankAccount(bankAccount);
		loanApp.setLoan(loan);
		loanApp.setClient(client);
		loanApp.setPayedDeadlines(0);
	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Validator validator = createValidator();

	@ParameterizedTest
	@ValueSource(doubles = { 100.00, 100.01, 50000.00, 999999.99, 1000000.00 })
	void positiveTestWithAmount(Double amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loanApp.setAmount(amount);

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		Assertions.assertThat(constraintViolations.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 99.99, 1000000.01 })
	void negativeTestWithAmount(Double amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loanApp.setAmount(amount);

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		if (amount < 100.00) {
			Assertions.assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 100.00");
		} else {
			Assertions.assertThat(violation.getMessage()).isEqualTo("must be less than or equal to 1000000.00");
		}
	}

	@ParameterizedTest
	@ValueSource(doubles = { 600.00, 600.01, 50000.00, 999999.99, 1000000.00 })
	void positiveTestWithIncome(Double income) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		Assertions.assertThat(constraintViolations.isEmpty());
	}

	@Test
	void shouldNotValidateWhenPurposeBlank() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loanApp.setPurpose("");

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotBlankMessage();
	}
	
	@Test
	void shouldNotValidateWhenStatusBlank() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loanApp.setStatus(null);

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotBlankMessage();
	}
	
	@Test
	void shouldNotValidateWhenStatusNotMatchPattern() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loanApp.setStatus("acepppppted");

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsPatternStatusLAppMessage();
	}

	@Test
	void shouldNotValidateWhenAmountPaidNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loanApp.setAmount_paid(null);

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

	@Test
	void shouldNotValidateWhenDestinationNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loanApp.setBankAccount(null);

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

	@Test
	void shouldNotValidateWhenLoanNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loanApp.setLoan(null);

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

	@Test
	void shouldNotValidateWhenClientNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loanApp.setClient(null);

		Set<ConstraintViolation<LoanApplication>> constraintViolations = validator.validate(loanApp);

		ConstraintViolation<LoanApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

}
