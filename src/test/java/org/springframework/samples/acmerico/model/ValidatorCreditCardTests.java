package org.springframework.samples.acmerico.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.samples.acmerico.util.ViolationAssertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorCreditCardTests {

	private static Client client = new Client();
	private static BankAccount bankAccount = new BankAccount();
	private static CreditCard cc = new CreditCard();
	private static CreditCardApplication ccApp = new CreditCardApplication();

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

		ccApp.setBankAccount(bankAccount);
		ccApp.setClient(client);
		ccApp.setStatus("PENDING");
	}

	@BeforeEach
	private void createCreditCard() {
		cc.setNumber("4880576829512546");
		cc.setDeadline("12/2025");
		cc.setCvv("123");
		cc.setBankAccount(bankAccount);
		cc.setClient(client);
		cc.setCreditCardApplication(ccApp);
	}

	@Test
	void shouldNotValidateWhenNumberIncorrect() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		cc.setNumber("543534");

		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsInvalidCCNumberMessage();
	}

	@Test
	void shouldNotValidateWhenNumberEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		cc.setNumber(null);

		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotBlankMessage();
	}

	@Test
	void shouldNotValidateWhenDeadlineIncorrect() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		cc.setDeadline("12/20a5");

		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsDeadlineCCMessage();
	}

	@Test
	void shouldNotValidateWhenDeadlineEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		cc.setDeadline(null);

		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotBlankMessage();
	}

	@Test
	void shouldNotValidateWhenCvvIncorrect() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		cc.setCvv("1293");

		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsCVVMessage();
	}

	@Test
	void shouldNotValidateWhenCvvEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		cc.setCvv(null);

		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotBlankMessage();
	}

	@Test
	void shouldNotValidateWhenClientEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		cc.setClient(null);

		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

	@Test
	void shouldNotValidateWhenBankAccountEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		cc.setBankAccount(null);

		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		assertThat(violation).throwsNotNullMessage();
	}

}
