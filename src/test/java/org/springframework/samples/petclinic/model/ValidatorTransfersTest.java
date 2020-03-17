package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorTransfersTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenAccountNumberNull() {
		
		Transfer transfer = new Transfer();
		transfer.setAccountNumber(null);
		transfer.setAmount(200.0);
		transfer.setDestination("ES23 2323 2323 2323 2323");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transfer);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Transfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("accountNumber");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");
		
	}
	
	@Test
	void shouldNotValidateWhenAccountNumberIncorrect() {
		
		Transfer transfer = new Transfer();
		transfer.setAccountNumber("ES23 2323 2323 2323");
		transfer.setAmount(200.0);
		transfer.setDestination("ES23 2323 2323 2323 2323");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transfer);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Transfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("accountNumber");
		assertThat(violation.getMessage()).isEqualTo("Invalid account number");
		
	}
	
	@Test
	void shouldNotValidateWhenAmountIncorrect() {
		Transfer transfer = new Transfer();
		transfer.setAccountNumber("ES23 2323 2323 2323 2424");
		transfer.setAmount(90.0);
		transfer.setDestination("ES23 2323 2323 2323 2323");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transfer);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Transfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("amount");
		assertThat(violation.getMessage()).isEqualTo("The amount must be greater than € 100, please if you need a smaller amount make an instant transfer");
		
	}
	
	@Test
	void shouldNotValidateWhenDestinationNull() {	
		Transfer transfer = new Transfer();
		transfer.setAccountNumber("ES23 2323 2323 2323 2424");
		transfer.setAmount(200.0);
		transfer.setDestination(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transfer);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Transfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("destination");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");
		
	}
	
	@Test
	void shouldNotValidateWhenDestinationIncorrect() {	
		Transfer transfer = new Transfer();
		transfer.setAccountNumber("ES23 2323 2323 2323 2424");
		transfer.setAmount(200.0);
		transfer.setDestination("ES23 2323 2323 2323");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transfer);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Transfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("destination");
		assertThat(violation.getMessage()).isEqualTo("Invalid account number");
		
	}
}
