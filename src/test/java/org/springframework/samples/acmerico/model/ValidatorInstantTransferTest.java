package org.springframework.samples.acmerico.model;

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorInstantTransferTest {

	private static InstantTransfer instantTransfer = new InstantTransfer();
	private static Client client = new Client();
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
	
	
	@ParameterizedTest
	@ValueSource(doubles = {10.,20.,50.,70.,80.})
	void positiveTestWithNormalCases(Double amount) {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		instantTransfer.setAmount(amount);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		
		assertThat(constraintViolations.size() == 0);
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {0.01,0.011,99.,100.})
	void positiveTestWithLimitCases(Double amount) {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		instantTransfer.setAmount(amount);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		
		assertThat(constraintViolations.size() == 0);
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {-30.,-0.1,0.,0.009})
	void negativeTestWithInsufficientAmount(Double amount) {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		instantTransfer.setAmount(amount);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0.01");
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {101.,200.,1000.,4000.})
	void negativeTestWithExceededAmount(Double amount) {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		instantTransfer.setAmount(amount);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(bankAccount);
		instantTransfer.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must be less than or equal to 100.00");
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
		
		instantTransfer.setAmount(90.00);
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
		
		instantTransfer.setAmount(90.00);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		instantTransfer.setBankAccount(null);
		instantTransfer.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}
}
