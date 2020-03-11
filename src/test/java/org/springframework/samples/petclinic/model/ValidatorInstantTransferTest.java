package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorInstantTransferTest {

	private static InstantTransfer instantTransfer = new InstantTransfer();

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
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("amount");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0.01");
	}

	@Test
	void shouldNotValidateWhenAmountMax() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		instantTransfer.setAmount(201.00);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("amount");
		assertThat(violation.getMessage()).isEqualTo("must be less than or equal to 200.00");
	}
	
	@Test
	void shouldNotValidateWhenAmountEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		instantTransfer.setAmount(null);
		instantTransfer.setDestination("ES23 2323 2323 2323 2323");
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("amount");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenAccountNumberPattern() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		instantTransfer.setAmount(20.00);
		instantTransfer.setDestination("23 2323 2323 2323 2323");
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("destination");
		assertThat(violation.getMessage()).isEqualTo("Invalid account number");
	}
	
	@Test
	void shouldNotValidateWhenAccountNumberEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		instantTransfer.setAmount(20.00);
		instantTransfer.setDestination(null);
		Validator validator = createValidator();
		Set<ConstraintViolation<InstantTransfer>> constraintViolations = validator.validate(instantTransfer);
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<InstantTransfer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("destination");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
}
