package org.springframework.samples.acmerico.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorClientsTest {

	private static Client client = new Client();

	@BeforeAll
	static void setUser() {
		User new_user = new User();
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		client.setUser(new_user);
	}

	@BeforeEach
	private void resetClient() {
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		client.setFirstName("Name");
		client.setLastName("Surname");
		client.setAddress("Address");
		client.setAge(20);
		client.setBirthDate(bday);
		client.setCity("Sevilla");
		client.setJob("Empresario");
		client.setLastEmployDate(lastEmployDate);
		client.setMaritalStatus("Casado");
		client.setSalaryPerYear(20000.00);
	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Validator validator = createValidator();

	@Test
	public void testCreationClientsWhitoutAddress() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		client.setAddress("");

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	public void testCreationClientsWhitoutAge() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		client.setAge(null);

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	public void testCreationClientsWhitoutBirthday() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		client.setBirthDate(null);

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	public void testCreationClientsWhitoutCity() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		client.setCity("");

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	public void testCreationClientsWhitoutJob() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		client.setJob("");

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	public void testCreationClientsWhitoutMaritalStatus() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		client.setMaritalStatus("");

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	public void testCreationClientsWhitoutSalaryPerYear() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		client.setSalaryPerYear(null);

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
}
