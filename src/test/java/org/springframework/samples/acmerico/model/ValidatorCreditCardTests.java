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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorCreditCardTests {
	
	private static Client client = new Client();
	private static BankAccount bankAccount = new BankAccount();
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
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
	
	@Test
	void shouldNotValidateWhenNumberIncorrect() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("543534");
		cc.setDeadline("12/2025");
		cc.setCvv("123");
		cc.setBankAccount(bankAccount);
		cc.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("invalid credit card number");
		
	}
	
	@Test
	void shouldNotValidateWhenNumberEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber(null);
		cc.setDeadline("12/2025");
		cc.setCvv("123");
		cc.setBankAccount(bankAccount);
		cc.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
		
	}
	
	@Test
	void shouldNotValidateWhenDeadlineIncorrect() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("5509189773541186");
		cc.setDeadline("12/20a5");
		cc.setCvv("123");
		cc.setBankAccount(bankAccount);
		cc.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("Incorrect deadline");
		
	}
	
	@Test
	void shouldNotValidateWhenDeadlineEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("5509189773541186");
		cc.setDeadline(null);
		cc.setCvv("123");
		cc.setBankAccount(bankAccount);
		cc.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
		
	}
	
	@Test
	void shouldNotValidateWhenCvvIncorrect() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("5509189773541186");
		cc.setDeadline("12/2025");
		cc.setCvv("1293");
		cc.setBankAccount(bankAccount);
		cc.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("Incorrect CVV");
		
	}
	
	@Test
	void shouldNotValidateWhenCvvEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("5509189773541186");
		cc.setDeadline("12/2025");
		cc.setCvv(null);
		cc.setBankAccount(bankAccount);
		cc.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
		
	}
	
	@Test
	void shouldNotValidateWhenClientEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("5509189773541186");
		cc.setDeadline("12/2025");
		cc.setCvv("676");
		cc.setBankAccount(bankAccount);
		cc.setClient(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}
	
	@Test
	void shouldNotValidateWhenBankAccountEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("5509189773541186");
		cc.setDeadline("12/2025");
		cc.setCvv("342");
		cc.setBankAccount(null);
		cc.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}

}
