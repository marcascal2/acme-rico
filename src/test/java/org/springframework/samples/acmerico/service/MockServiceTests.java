package org.springframework.samples.acmerico.service;

import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.model.CreditCardApplication;
import org.springframework.samples.acmerico.model.Employee;
import org.springframework.samples.acmerico.model.InstantTransfer;
import org.springframework.samples.acmerico.model.TransferApplication;
import org.springframework.samples.acmerico.model.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MockServiceTests {

	private static final Integer TEST_CLIENT_ID = 1;

	private static final Integer TEST_BANK_ACCOUNT_ID = 1;
	private static final String TEST_BANK_ACCOUNT_NUMBER = "ES23 0025 0148 1259 1424";

	private static final String TEST_CLIENT_USERNAME = "client1";

	private static final Integer TEST_TRANSFER_ID = 1;
		
	private static final Integer TEST_CARD_ID = 1;

	private static final Integer TEST_EMPLOYEE_ID = 1;
	
	private static final Integer TEST_CREDITCARDAPP_ID = 1;

	@MockBean
	private ClientService clientService;

	@MockBean
	private BankAccountService accountService;

	@MockBean
	private TransferAppService transferAppService;
	
	@MockBean
	private InstantTransferService instantTransferService;

	@MockBean
	private CreditCardService creditCardService;
	
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
    private CreditCardAppService creditCardAppService;
    
	
	private InstantTransfer instant;
	private TransferApplication transfer;
	private Client client;
	private User user;
	private BankAccount account;
	private CreditCard creditCard;
	private Employee employee;
	private CreditCardApplication application;
	
	@BeforeEach
	void setup() {
		employee = new Employee();
		client = new Client();
		account = new BankAccount();
		user = new User();
		transfer = new TransferApplication();
		creditCard = new CreditCard();
        application = new CreditCardApplication();
	
		user.setUsername(TEST_CLIENT_USERNAME);
		user.setPassword("client1");
		user.setEnabled(true);

		employee.setId(TEST_EMPLOYEE_ID);
		employee.setFirstName("Jose");
		employee.setLastName("Garcia Dorado");
		employee.setSalary(2000.0);
		employee.setUser(user);
		
		this.employeeService.saveEmployee(employee);
		
		final LocalDate bithday = LocalDate.of(1998, 11, 27);
		final LocalDate lEmpDate = LocalDate.of(2010, 01, 22);
		final LocalDateTime creationDate = LocalDateTime.of(2019, 11, 23, 12, 12, 12);

		client.setId(TEST_CLIENT_ID);
		client.setFirstName("client");
		client.setLastName("Ruiz");
		client.setAddress("Gordal");
		client.setBirthDate(bithday);
		client.setCity("Sevilla");
		client.setMaritalStatus("single but whole");
		client.setSalaryPerYear(300000.);
		client.setAge(21);
		client.setJob("student");
		client.setLastEmployDate(lEmpDate);
		client.setUser(user);

		this.clientService.saveClient(client);
		
		account.setId(TEST_BANK_ACCOUNT_ID);
		account.setAccountNumber(TEST_BANK_ACCOUNT_NUMBER);
		account.setAlias("Cuenta personal");
		account.setAmount(10000.);
		account.setCreatedAt(creationDate);
		account.setClient(client);

		this.accountService.saveBankAccount(account);
		
		transfer.setId(TEST_TRANSFER_ID);
		transfer.setStatus("PENDING");
		transfer.setAmount(1000.);
		transfer.setAccount_number_destination("ES44 4523 9853 3674 4366");
		transfer.setBankAccount(account);
		transfer.setClient(client);
		
		this.transferAppService.save(transfer);
		
		creditCard.setId(TEST_CARD_ID);
		creditCard.setNumber("5130218133680652");
		creditCard.setDeadline("07/2022");
		creditCard.setCvv("156");
		
		application.setId(TEST_CREDITCARDAPP_ID);
        application.setStatus("PENDING");
        application.setBankAccount(account);
        application.setClient(client);

        this.creditCardAppService.save(application);
	}

	@WithMockUser(value = "spring")
	@Test
	public void testFindClientById() {
		Client c = this.clientService.findClientById(client.getId());
		verify(clientService).findClientById(TEST_CLIENT_ID);
	}

	@WithMockUser(value = "spring")
	@Test
	public void testFindAccountById() {
		BankAccount b = this.accountService.findBankAccountById(account.getId());
		verify(accountService).findBankAccountById(TEST_BANK_ACCOUNT_ID);
	}

	@WithMockUser(value = "spring")
	@Test
	public void testFindBankAccountsByAccountNumber() {
		Collection<BankAccount> bankAccountsList = this.accountService
				.findBankAccountByAccountNumber(account.getAccountNumber());
		verify(accountService).findBankAccountByAccountNumber(account.getAccountNumber());
	}
	
	@WithMockUser(value = "spring")
	@Test
	public void testSaveInstantTransfer() {
		instant = new InstantTransfer();
		instant.setId(TEST_TRANSFER_ID);
		instant.setAmount(100.);
		instant.setDestination("ES44 4523 9853 3674 4366");
		instant.setBankAccount(account);
		instant.setClient(client);
		
		this.instantTransferService.save(instant);
		
		verify(instantTransferService).save(instant);
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	public void testFindTransferById() {
		TransferApplication t = this.transferAppService
				.findTransferAppById(transfer.getId());
		verify(transferAppService).findTransferAppById(TEST_TRANSFER_ID);
	}
	
	@WithMockUser(value = "spring")
	@Test
	public void testSaveCreditCard() {
		this.creditCardService.saveCreditCard(creditCard);
		
		verify(creditCardService).saveCreditCard(creditCard);
		
	}

	@WithMockUser(value = "spring")
	@Test
	public void testFindCardById() {
		CreditCard cc = this.creditCardService
				.findCreditCardById(creditCard.getId());
		verify(creditCardService).findCreditCardById(TEST_CARD_ID);
	}
	
	@WithMockUser(value = "spring")
	@Test
	public void testFindEmployeeById() {
		Employee e = this.employeeService
				.findEmployeeById(employee.getId());
		verify(employeeService).findEmployeeById(TEST_EMPLOYEE_ID);
	}
	
	@WithMockUser(value = "spring")
	@Test
	public void testDeleteEmployeeById() {
		this.employeeService
				.deleteEmployeeById(employee.getId());
		verify(employeeService).deleteEmployeeById(TEST_EMPLOYEE_ID);
	}
	
	@WithMockUser(value = "spring")
	@Test
	public void testFindCardAppById() {
		CreditCardApplication a = this.creditCardAppService
				.findCreditCardAppById(application.getId());
		verify(creditCardAppService).findCreditCardAppById(TEST_CREDITCARDAPP_ID);
	}
}
