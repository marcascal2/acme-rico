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
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorTransferApplicationTest {

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
	void shouldNotValidateWhenStatusNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus(null);
		transferApp.setAmount(200.0);
		transferApp.setAccount_number_destination("ES23 2323 2323 2323 2323");
		transferApp.setBankAccount(bankAccount);
		transferApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
		
	}
	
	@Test
	void shouldNotValidateWhenStatusIncorrect() {
		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("cualquier cosa");
		transferApp.setAmount(200.0);
		transferApp.setAccount_number_destination("ES23 2323 2323 2323 2323");
		transferApp.setBankAccount(bankAccount);
		transferApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("Transfer application status only can be ACCEPTED, REJECTED or PENDING");
		
	}
	
	@Test
	void shouldNotValidateWhenAmountIncorrect() {
		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("PENDING");
		transferApp.setAmount(90.0);
		transferApp.setAccount_number_destination("ES23 2323 2323 2323 2323");
		transferApp.setBankAccount(bankAccount);
		transferApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("The amount must be greater than € 100, please if you need a smaller amount make an instant transfer");
		
	}
	
	@Test
	void shouldNotValidateWhenDestinationNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("PENDING");
		transferApp.setAmount(200.0);
		transferApp.setAccount_number_destination(null);
		transferApp.setBankAccount(bankAccount);
		transferApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
		
	}
	
	@Test
	void shouldNotValidateWhenDestinationIncorrect() {
		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("PENDING");
		transferApp.setAmount(200.0);
		transferApp.setAccount_number_destination("ES23 2323 2323");
		transferApp.setBankAccount(bankAccount);
		transferApp.setClient(client);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("Invalid account number");
		
	}
}
