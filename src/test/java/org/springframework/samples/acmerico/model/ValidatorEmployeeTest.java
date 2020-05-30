package org.springframework.samples.acmerico.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.samples.acmerico.util.ViolationAssertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorEmployeeTest {

	private static Employee employee = new Employee();
	private static User user = new User();

	@BeforeAll
	static void createUser() {
		user.setUsername("user");
		user.setPassword("pass");
	}

	@BeforeEach
	private void resetUser() {
		employee.setFirstName("Name");
		employee.setLastName("Surname");
		employee.setSalary(200.00);
		employee.setUser(user);
	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Validator validator = createValidator();

	@Test
	void shouldNotValidateWhenFirstNameEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		employee.setFirstName(null);

		Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

		ConstraintViolation<Employee> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotEmptyMessage();
	}

	@Test
	void shouldNotValidateWhenLastNameEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		employee.setLastName(null);

		Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

		ConstraintViolation<Employee> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotEmptyMessage();
	}

	@Test
	void shouldNotValidateWhenSalaryEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		employee.setSalary(null);

		Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

		ConstraintViolation<Employee> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

	@Test
	void shouldNotValidateWhenUserEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		employee.setUser(null);

		Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

		ConstraintViolation<Employee> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

}
