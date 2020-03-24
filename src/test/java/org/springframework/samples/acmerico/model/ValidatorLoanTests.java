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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorLoanTests {

	private static BankAccount bankAccount = new BankAccount();
	private static Client client = new Client();
	private static User user = new User();
	private static Loan loan = new Loan();

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
	static void populateBankAccount() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);
	}

	@BeforeEach
	private void resetLoan() {
		loan.setMinimum_amount(200.);
		loan.setMinimum_income(700.);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		loan.setClient(client);
	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Validator validator = createValidator();

	@ParameterizedTest
	@ValueSource(doubles = { 200., 5000., 700000., 900000. })
	void positiveTestWithNormalCases(Double minimum_amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setMinimum_amount(minimum_amount);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 100.00, 100.01, 999999.99, 1000000.00 })
	void positiveTestWithLimitCases(Double minimum_amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setMinimum_amount(minimum_amount);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 99.99, 1000000.01 })
	void negativeTestWithMinimunAmount(Double minimum_amount) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setMinimum_amount(minimum_amount);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		if (minimum_amount < 100.00) {
			assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 100.00");
		} else {
			assertThat(violation.getMessage()).isEqualTo("must be less than or equal to 1000000.00");
		}
	}

	@ParameterizedTest
	@ValueSource(doubles = { 600.00, 600.01, 50000.00, 999999.99, 1000000.00 })
	void positiveTestWithMinimunIncome(Double minimum_income) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setMinimum_income(minimum_income);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 599.99, 1000000.01 })
	void negativeTestWithMinimunIncome(Double minimum_income) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setMinimum_income(minimum_income);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		if (minimum_income < 600.00) {
			assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 600.00");
		} else {
			assertThat(violation.getMessage()).isEqualTo("must be less than or equal to 1000000.00");
		}
	}

	@ParameterizedTest
	@ValueSource(ints = { 2, 3, 500 })
	void positiveTestWithNumberOfDealines(int number_of_deadlines) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setNumber_of_deadlines(number_of_deadlines);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(ints = { -1, 1 })
	void negativeTestWithNumberOfDealines(int number_of_deadlines) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setNumber_of_deadlines(number_of_deadlines);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 2");
	}

	@ParameterizedTest
	@ValueSource(doubles = { .0, 100.0, 5000.0 })
	void positiveTestWithOpeningPrice(Double opening_price) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setOpening_price(opening_price);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(doubles = { -1.0, -100.0 })
	void negativeTestWithOpeningPrice(Double opening_price) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setOpening_price(opening_price);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0.01, 0.02, 1.01 })
	void positiveTestWithMonthlyFee(Double monthly_fee) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setMonthly_fee(monthly_fee);

		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(doubles = { .0, -0.01, -1.01 })
	void negativeTestWithMonthlyFee(Double monthly_fee) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setMonthly_fee(monthly_fee);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0.01");
	}

	@Test
	void shouldNotValidateWhenSinglesLoanNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setSingle_loan(null);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenClientNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		loan.setClient(null);

		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

}
