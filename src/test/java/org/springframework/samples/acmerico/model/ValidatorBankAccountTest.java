package org.springframework.samples.acmerico.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.samples.acmerico.util.ViolationAssertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorBankAccountTest {

	private static Client client = new Client();
	private static BankAccount bankAccount = new BankAccount();

	@BeforeAll
	static void createClientAndBankAccount() {
		Set<BankAccount> bankAccounts = new HashSet<BankAccount>();
		bankAccounts.add(bankAccount);
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(899.0);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setAlias("menos de 30 caracteres");
		bankAccount.setClient(client);
		client.setAddress("address");
		client.setAge(20);
		client.setBankAccounts(bankAccounts);
		client.setBirthDate(LocalDate.of(1999, 11, 05));
		client.setCity("Sevilla");
		client.setFirstName("Antonio");
		client.setJob("student");
		client.setLastName("Marquez");
		client.setMaritalStatus("single");
		client.setSalaryPerYear(0.00);
	}

	@BeforeEach
	private void resetBankAccount() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(899.0);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setAlias("menos de 30 caracteres");
		bankAccount.setClient(client);
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
		bankAccount.setAmount(null);

		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);

		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

	@Test
	void shouldNotValidateWhenAccountNumberEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setAccountNumber(null);

		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);

		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

	@Test
	void shouldNotValidateWhenAccountNumberPattern() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setAccountNumber("23 2323 2323 2323 2323");

		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);

		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsInvalidAccountNumberMessage();
	}

	@Test
	void shouldNotValidateWhenClientEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setClient(null);

		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);

		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

	@Test
	void shouldNotValidateWhenAliasLength() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setAlias("esto es un string con mas de 30 caracteres");

		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);

		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsLengthAccountAliasMessage();
	}

	@Test
	void shouldNotValidateWhenNotCreatedAtPast() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setCreatedAt(LocalDateTime.of(2021, 2, 1, 17, 30));

		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);

		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsPastDateMessage();
	}

	@Test
	void shouldNotValidateWhenCreatedAtEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setCreatedAt(null);

		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);

		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

}
