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

public class ValidatorTransferApplicationTest {

	private static BankAccount bankAccount = new BankAccount();
	private static Client client = new Client();
	private static User user = new User();
	private static TransferApplication transferApp = new TransferApplication();

	
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
	
	@BeforeEach
	private void resetTransferApp() {
		transferApp.setStatus("PENDING");
		transferApp.setAmount(200.0);
		transferApp.setAccount_number_destination("ES23 2323 2323 2323 2323");
		transferApp.setBankAccount(bankAccount);
		transferApp.setClient(client);
	}
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	private Validator validator = createValidator();

	
	@Test
	void shouldNotValidateWhenStatusNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		transferApp.setStatus(null);

		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotEmptyMessage();
		
	}
	
	@Test
	void shouldNotValidateWhenIncorrectStatus() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		transferApp.setStatus("cualquier cosa");
	
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsPatternStatusTAppMessage();
		
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {150.,200.,5000.,70000.,800000.})
	void positiveTestWithNormalCases(Double amount) {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
	
		transferApp.setAmount(amount);
	
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		Assertions.assertThat(constraintViolations.size() == 0);
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {100.,100.011})
	void positiveTestWithLimitCases(Double amount) {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		transferApp.setAmount(amount);

		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		Assertions.assertThat(constraintViolations.size() == 0);
	}
	
	@ParameterizedTest
	@ValueSource(doubles = {-30.,-0.1,50.,99.,99.9999})
	void negativeTestWithInsufficientAmount(Double amount) {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		transferApp.setAmount(amount);

		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsMinimumTransferAmountMessage();
	}
	
	@Test
	void shouldNotValidateWhenAmountEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		transferApp.setAmount(null);
		
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
		
	}
	
	@Test
	void shouldNotValidateWhenDestinationNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		transferApp.setAccount_number_destination(null);
		
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotEmptyMessage();
		
	}
	
	@Test
	void shouldNotValidateWhenDestinationIncorrect() {
	
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		transferApp.setAccount_number_destination("ES23 2323 2323");
		
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsInvalidAccountNumberMessage();
		
	}
}
