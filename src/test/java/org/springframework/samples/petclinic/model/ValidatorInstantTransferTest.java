package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorInstantTransferTest {

	private static InstantTransfer instantTransfer = new InstantTransfer();
	private static Client client=new Client();
	private static BankAccount bankAccount = new BankAccount();

	@BeforeAll
	static void createClientAndBankAccount() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(200.00);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setAlias("menos de 30 caracteres");
		bankAccount.setClient(client);
		Set<BankAccount> bankAccounts=new HashSet<BankAccount>();
		bankAccounts.add(bankAccount);
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
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenAmountMin() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		instantTransfer.setAmount(0.00);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(client);
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0.01");
	}

	@Test
	void shouldNotValidateWhenAmountMax() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		instantTransfer.setAmount(201.00);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(client);
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be less than or equal to 200.00");
	}
	
	@Test
	void shouldNotValidateWhenAmountEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		instantTransfer.setAmount(null);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(client);
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenDestinationPattern() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		instantTransfer.setAmount(20.00);
		instantTransfer.setDestination("23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(client);
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("Invalid account number");
	}
	
	@Test
	void shouldNotValidateWhenDestinationEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		instantTransfer.setAmount(20.00);
		instantTransfer.setDestination(null);
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(client);
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenClientEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		instantTransfer.setAmount(200.00);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}
	
	@Test
	void shouldNotValidateWhenBankAccountEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		instantTransfer.setAmount(200.00);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(null);
		instantTransfer.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}
}
