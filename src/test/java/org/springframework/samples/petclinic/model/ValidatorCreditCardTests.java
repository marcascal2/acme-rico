package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorCreditCardTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenNumberIncorrect() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("543534");
		cc.setDeadline("12/2025");
		cc.setCvv("123");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("number");
		assertThat(violation.getMessage()).isEqualTo("invalid credit card number");
		
	}
	
	@Test
	void shouldNotValidateWhenDeadlineIncorrect() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("5509189773541186");
		cc.setDeadline("12/20a5");
		cc.setCvv("123");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("deadline");
		assertThat(violation.getMessage()).isEqualTo("Incorrect deadline");
		
	}
	
	@Test
	void shouldNotValidateWhenCvvIncorrect() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCard cc = new CreditCard();
		cc.setNumber("5509189773541186");
		cc.setDeadline("12/2025");
		cc.setCvv("1293");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("cvv");
		assertThat(violation.getMessage()).isEqualTo("Incorrect CVV");
		
	}

}
