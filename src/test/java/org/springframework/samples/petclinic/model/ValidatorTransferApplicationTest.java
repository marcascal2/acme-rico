package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorTransferApplicationTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenStatusNull() {
		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus(null);
		transferApp.setAmount(200.0);
		transferApp.setAccount_number_destination("ES23 2323 2323 2323 2323");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("status");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");
		
	}
	
	@Test
	void shouldNotValidateWhenStatusIncorrect() {
		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("cualquier cosa");
		transferApp.setAmount(200.0);
		transferApp.setAccount_number_destination("ES23 2323 2323 2323 2323");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("status");
		assertThat(violation.getMessage()).isEqualTo("Transfer application status only can be ACCEPTED, REJECTED or PENDING");
		
	}
	
	@Test
	void shouldNotValidateWhenAmountIncorrect() {
		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("PENDING");
		transferApp.setAmount(90.0);
		transferApp.setAccount_number_destination("ES23 2323 2323 2323 2323");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("amount");
		assertThat(violation.getMessage()).isEqualTo("The amount must be greater than € 100, please if you need a smaller amount make an instant transfer");
		
	}
	
	@Test
	void shouldNotValidateWhenDestinationNull() {
		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("PENDING");
		transferApp.setAmount(200.0);
		transferApp.setAccount_number_destination(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("account_number_destination");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");
		
	}
	
	@Test
	void shouldNotValidateWhenDestinationIncorrect() {
		
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("PENDING");
		transferApp.setAmount(200.0);
		transferApp.setAccount_number_destination("ES23 2323 2323");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<TransferApplication>> constraintViolations = validator.validate(transferApp);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<TransferApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("account_number_destination");
		assertThat(violation.getMessage()).isEqualTo("Invalid account number");
		
	}
}
