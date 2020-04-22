package org.springframework.samples.acmerico.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorDebtTest {

	private static Client client = new Client();
	private static LoanApplication loanApplication = new LoanApplication();
	private static Debt debt = new Debt();
	private static BankAccount bankAccount = new BankAccount();
	private static Loan loan = new Loan();

	@BeforeAll
	static void createClient() {
		client.setAddress("address");
		client.setAge(20);
		client.setBirthDate(LocalDate.of(1999, 11, 05));
		client.setCity("Sevilla");
		client.setFirstName("Antonio");
		client.setJob("student");
		client.setLastName("Marquez");
		client.setMaritalStatus("single");
		client.setSalaryPerYear(0.00);
	}

	@BeforeAll
	static void createBankAccount() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setClient(client);
	}

	@BeforeAll
	static void createLoan() {
		loan.setDescription("This is a loan");
		loan.setMinimum_amount(200.);
		loan.setMinimum_income(700.);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
	}

	@BeforeAll
	static void createLoanApp() {
		loanApplication.setAmount_paid(50.0);
		loanApplication.setClient(client);
		loanApplication.setDestination(bankAccount);
		loanApplication.setLoan(loan);
		loanApplication.setPurpose("purpose");
		loanApplication.setStatus("ACCEPTED");
	}

	@BeforeEach
	private void resetDebt() {
		debt.setAmount(100.0);
		debt.setClient(client);
		debt.setLoanApplication(loanApplication);
		debt.setRefreshDate("02/2019");
	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Validator validator = createValidator();

	@Test
	void shouldNotValidateWhenAmountEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		debt.setAmount(null);

		Set<ConstraintViolation<Debt>> constraintViolations = validator.validate(debt);

		ConstraintViolation<Debt> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@ParameterizedTest
	@ValueSource(doubles = { 10., 50, 300., 1000. })
	void positiveAmountWithNormalAmount(Double amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		debt.setAmount(amount);

		Set<ConstraintViolation<Debt>> constraintViolations = validator.validate(debt);

		assertThat(constraintViolations.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0., 0.0001, 0.2 })
	void positiveAmountWithLimitAmount(Double amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		debt.setAmount(amount);

		Set<ConstraintViolation<Debt>> constraintViolations = validator.validate(debt);

		assertThat(constraintViolations.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(doubles = { -10., -1., -0.9999 })
	void negativeAmountWithMinimunAmount(Double amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		debt.setAmount(amount);

		Set<ConstraintViolation<Debt>> constraintViolations = validator.validate(debt);

		ConstraintViolation<Debt> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");
	}
	
	

	@Test
	void shouldNotValidateWhenRefreshDateNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		debt.setRefreshDate(null);

		Set<ConstraintViolation<Debt>> constraintViolations = validator.validate(debt);

		ConstraintViolation<Debt> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateWhenRefreshDateIncorrect() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		debt.setRefreshDate("009/21");

		Set<ConstraintViolation<Debt>> constraintViolations = validator.validate(debt);

		ConstraintViolation<Debt> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("Incorrect refresh date");
	}

	@Test
	void shoulNotValidateWhenLoanAppNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		debt.setLoanApplication(null);

		Set<ConstraintViolation<Debt>> constraintViolations = validator.validate(debt);

		ConstraintViolation<Debt> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shoulNotValidateWhenClientNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		debt.setClient(null);

		Set<ConstraintViolation<Debt>> constraintViolations = validator.validate(debt);

		ConstraintViolation<Debt> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
}
