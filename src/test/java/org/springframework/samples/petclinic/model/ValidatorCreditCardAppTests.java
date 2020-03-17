package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorCreditCardAppTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenStatusBlank() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		CreditCardApplication ccApp = new CreditCardApplication();
		ccApp.setStatus("");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<CreditCardApplication>> constraintViolations = validator.validate(ccApp);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<CreditCardApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("status");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

}
