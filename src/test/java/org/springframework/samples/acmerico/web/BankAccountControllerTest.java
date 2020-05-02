package org.springframework.samples.acmerico.web;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.FilterType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.hamcrest.Matchers.*;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


@WebMvcTest(controllers = BankAccountController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class BankAccountControllerTest {

    private static final Integer TEST_CLIENT_ID = 1;
    private static final Integer TEST_BANK_ACCOUNT_ID = 1;
    private static final String TEST_BANK_ACCOUNT_NUMBER = "ES23 0025 0148 1259 1424";
    private static final String TEST_CLIENT_USERNAME = "client1";

    @MockBean
    private ClientService clientService;

    @MockBean
    private BankAccountService bankAccountService;

    @Autowired
    private MockMvc mockMvc;

    private Client client;
    private User user;
    private BankAccount account;
    private BankAccount account2;

    @BeforeEach
    void setup() {
        client = new Client();
        account = new BankAccount();
        account2 = new BankAccount();
        user = new User();

        Collection<BankAccount> accounts = new ArrayList<BankAccount>();

        user.setUsername(TEST_CLIENT_USERNAME);
        user.setPassword("client1");
        user.setEnabled(true);

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

        account.setAccountNumber(TEST_BANK_ACCOUNT_NUMBER);
        account.setAlias("Cuenta personal");
        account.setAmount(10000.);
        account.setCreatedAt(creationDate);
        account.setClient(client);

        account2.setAccountNumber("ES23 0025 2222 1259 1424");
        account2.setAlias("Segunda Cuenta personal");
        account2.setAmount(5000.);
        account2.setCreatedAt(creationDate);
        account2.setClient(client);

        accounts.add(account);
        accounts.add(account2);

        when(clientService.findClientById(TEST_CLIENT_ID)).thenReturn(client);
        when(bankAccountService.findBankAccountById(TEST_BANK_ACCOUNT_ID)).thenReturn(account);
        when(bankAccountService.findBankAccounts()).thenReturn(accounts);
        when(bankAccountService.findBankAccountByClient(client)).thenReturn(accounts);
        when(bankAccountService.findBankAccountByNumber(TEST_BANK_ACCOUNT_NUMBER)).thenReturn(account);
        when(clientService.findBankAccountsByUsername(TEST_CLIENT_USERNAME)).thenReturn(accounts);
        when(clientService.findClientByUserName(TEST_CLIENT_USERNAME)).thenReturn(client);

    }

    // List all account by username test: Terminar
    @WithMockUser(username = "client1",roles = {"client"})
    @Test
    void testShowClientsAccounts() throws Exception {
        mockMvc.perform(get("/accounts"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(model().attributeExists("accounts"))
        .andExpect(model().attributeExists("clientId"))
        .andExpect(view().name("accounts/accounts"));


        // verify(clientService).findClientByLastName(TEST_CLIENT_USERNAME);
        // verify(clientService).findBankAccountsByUsername(TEST_CLIENT_USERNAME);
       
    }

    // Test de crear bankAccount
    @WithMockUser(value = "spring")
    @Test

    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/accounts/{clientId}/new", TEST_CLIENT_ID)).andExpect(status().isOk())
                .andExpect(model().attributeExists("bankAccount")).andExpect(model().attributeDoesNotExist("alias"))
                .andExpect(status().is2xxSuccessful()).andExpect(view().name("accounts/createAccountForm"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormWithErrors() throws Exception {
        // ModelAndView modelAndView = Mockito.mock(ModelAndView.class);

        mockMvc.perform(
                post("/accounts/{clientId}/new", TEST_CLIENT_ID).param("accountNumber", TEST_BANK_ACCOUNT_NUMBER)
                        .param("amount", "10000.").param("alias", "Cuenta Personal"))
                .andExpect(status().is4xxClientError());

    }

    // Show test
    @WithMockUser(value = "spring")
    @Test

    void testShowClientById() throws Exception {

        final LocalDateTime creationDate = LocalDateTime.of(2019, 11, 23, 12, 12, 12);

        mockMvc.perform(get("/accounts/{accountId}", TEST_BANK_ACCOUNT_ID))
                .andExpect(model().attributeExists("bankAccount"))
                .andExpect(model().attribute("bankAccount", hasProperty("accountNumber", is(TEST_BANK_ACCOUNT_NUMBER))))
                .andExpect(model().attribute("bankAccount", hasProperty("amount", is(10000.0))))
                .andExpect(model().attribute("bankAccount", hasProperty("createdAt", is(creationDate))))
                .andExpect(model().attribute("bankAccount", hasProperty("client", is(client))))
                .andExpect(status().is2xxSuccessful()).andExpect(view().name("accounts/showAccountInfo"));

        verify(bankAccountService).findBankAccountById(TEST_BANK_ACCOUNT_ID);

    }

    // Test delete
    @WithMockUser(value = "spring")
    @Test
    void testDeleteSuccess() throws Exception {

        mockMvc.perform(get("/accounts/{accountId}/delete", TEST_BANK_ACCOUNT_ID))
                .andExpect(view().name("redirect:/accounts/")).andExpect(status().is3xxRedirection());

        verify(bankAccountService).findBankAccountById(TEST_BANK_ACCOUNT_ID);
        verify(bankAccountService).deleteAccount(account);

    }

    // Test deposito money
    @WithMockUser(value = "spring")
    @Test

    void testInitDeposit() throws Exception {

        final LocalDateTime creationDate = LocalDateTime.of(2019, 11, 23, 12, 12, 12);

        mockMvc.perform(get("/accounts/{accountId}/depositMoney", TEST_BANK_ACCOUNT_ID))
                .andExpect(model().attributeExists("bankAccount"))
                .andExpect(model().attribute("bankAccount", hasProperty("accountNumber", is(TEST_BANK_ACCOUNT_NUMBER))))
                .andExpect(model().attribute("bankAccount", hasProperty("amount", is(10000.0))))
                .andExpect(model().attribute("bankAccount", hasProperty("createdAt", is(creationDate))))
                .andExpect(model().attribute("bankAccount", hasProperty("client", is(client))))
                .andExpect(status().is2xxSuccessful()).andExpect(view().name("accounts/depositMoney"));

        verify(bankAccountService).findBankAccountById(TEST_BANK_ACCOUNT_ID);

    }

    @WithMockUser(value = "spring")
    @Test

    void testMakeDepositSuccess() throws Exception {
        mockMvc.perform(post("/accounts/{accountId}/depositMoney", TEST_BANK_ACCOUNT_ID)
        .with(csrf())
        .param("accountNumber", "ES23 0025 2222 1259 1424")
        .param("alias", "Segunda Cuenta personal")
        .param("amount", "5000.0")
        .param("createdAt","2010/01/22"))
        .andExpect(view().name("redirect:/accounts/"));

    }

}