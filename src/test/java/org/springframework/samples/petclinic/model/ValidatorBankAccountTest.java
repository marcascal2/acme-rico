package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorBankAccountTest {
	
	private static Client client=new Client();
	private static BankAccount bankAccount = new BankAccount();
	
	@BeforeAll
	static void createClient() {
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
	void shouldNotValidateWhenAmountEmpty() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(null);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setAlias("menos de 30 caracteres");
		bankAccount.setClient(client);
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("amount");
		assertThat(violation.getMessage()).isEqualTo("no puede ser null");
	}

	@Test
	void shouldNotValidateWhenAccountNumberPattern() {
		bankAccount.setAccountNumber("23 2323 2323 2323 2323");
		bankAccount.setAmount(200.00);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setAlias("menos de 30 caracteres");
		bankAccount.setClient(client);
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("accountNumber");
		assertThat(violation.getMessage()).isEqualTo("Invalid account number");
	}
	
	@Test
	void shouldNotValidateWhenClientEmpty() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(200.00);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setAlias("menos de 30 caracteres");
		bankAccount.setClient(null);
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("client");
		assertThat(violation.getMessage()).isEqualTo("no puede ser null");
	}
	
	@Test
	void shouldNotValidateWhenAliasLength() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(200.00);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setAlias("esto es un string con mas de 30 caracteres");
		bankAccount.setClient(client);
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("alias");
		assertThat(violation.getMessage()).isEqualTo("la longitud tiene que estar entre 0 y 30");
	}
	
	@Test
	void shouldNotValidateWhenCreatedAtPast() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(200.00);
		bankAccount.setCreatedAt(LocalDateTime.of(2021, 2, 1, 17, 30));
		bankAccount.setAlias("menos de 30 caracteres");
		bankAccount.setClient(client);
		Validator validator = createValidator();
		Set<ConstraintViolation<BankAccount>> constraintViolations = validator.validate(bankAccount);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<BankAccount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("createdAt");
		assertThat(violation.getMessage()).isEqualTo("tiene que ser una fecha en el pasado");
	}
	
}
