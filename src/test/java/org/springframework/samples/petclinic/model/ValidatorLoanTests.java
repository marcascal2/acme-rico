package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorLoanTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenMinimunAmountDown100() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount(60.0);
		loan.setMinimum_income(700.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("minimum_amount");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 100.00");
		
	}
	
	@Test
	void shouldNotValidateWhenMinimunIncomeDown500() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(500.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("minimum_income");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 600.00");
		
	}
	
	@Test
	void shouldNotValidateWhenNumberOfDeadlinesDown2() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(900.0);
		loan.setNumber_of_deadlines(1);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("number_of_deadlines");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 2");
		
	}
	
	@Test
	void shouldNotValidateWhenOpeningPriceNegative() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(900.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(-12.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(true);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("opening_price");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");
		
	}
	
	@Test
	void shouldNotValidateWhenMonthlyFeeDown001() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(900.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.0);
		loan.setSingle_loan(true);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("monthly_fee");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0.01");
		
	}
	
	@Test
	void shouldNotValidateWhenSinglesLoanNull() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Loan loan = new Loan();
		loan.setMinimum_amount( 1550.0);
		loan.setMinimum_income(900.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(0.0);
		loan.setMonthly_fee(0.01);
		loan.setSingle_loan(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Loan>> constraintViolations = validator.validate(loan);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Loan> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("single_loan");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
	}

}
