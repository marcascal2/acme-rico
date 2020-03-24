package org.springframework.samples.acmerico.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorCreditCardAppTests {

	private static Client client = new Client();
	private static BankAccount bankAccount = new BankAccount();

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Validator validator = createValidator();

	@BeforeAll
	static void createClientAndBankAccount() {
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(200.00);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setAlias("menos de 30 caracteres");
		bankAccount.setClient(client);

		Set<BankAccount> bankAccounts = new HashSet<BankAccount>();
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

	@Test
	void shouldNotValidateWhenStatusBlank() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		CreditCardApplication ccApp = new CreditCardApplication();
		ccApp.setClient(client);
		ccApp.setBankAccount(bankAccount);
		ccApp.setStatus("");

		Set<ConstraintViolation<CreditCardApplication>> constraintViolations = validator.validate(ccApp);

		ConstraintViolation<CreditCardApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateWhenClientEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		CreditCardApplication ccApp = new CreditCardApplication();
		ccApp.setStatus("PENDING");
		ccApp.setBankAccount(null);
		ccApp.setClient(null);

		Set<ConstraintViolation<CreditCardApplication>> constraintViolations = validator.validate(ccApp);

		ConstraintViolation<CreditCardApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenBankAccountEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		CreditCardApplication ccApp = new CreditCardApplication();
		ccApp.setClient(client);
		ccApp.setStatus("PENDING");
		ccApp.setBankAccount(null);

		Set<ConstraintViolation<CreditCardApplication>> constraintViolations = validator.validate(ccApp);

		ConstraintViolation<CreditCardApplication> violation = constraintViolations.iterator().next();
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

}
