package org.springframework.samples.acmerico.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorBankAccountTest {
	
	private static Client client = new Client();
	private static BankAccount bankAccount = new BankAccount();
	
	@BeforeAll
	static void createClientAndBankAccount() {
		Set<BankAccount> bankAccounts=new HashSet<BankAccount>();
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

	@AfterEach
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

	@Test
	void shouldNotValidateWhenAmountEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setAmount(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenAccountNumberEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setAccountNumber(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenAccountNumberPattern() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setAccountNumber("23 2323 2323 2323 2323");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("Invalid account number");
	}
	
	@Test
	void shouldNotValidateWhenClientEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setClient(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenAliasLength() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setAlias("esto es un string con mas de 30 caracteres");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("length must be between 0 and 30");
	}
	
	@Test
	void shouldNotValidateWhenNotCreatedAtPast() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setCreatedAt(LocalDateTime.of(2021, 2, 1, 17, 30));

		
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);

		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be a past date");
	}
	
	@Test
	void shouldNotValidateWhenCreatedAtEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		bankAccount.setCreatedAt(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
}
