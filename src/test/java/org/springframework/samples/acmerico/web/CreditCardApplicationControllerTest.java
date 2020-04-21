package org.springframework.samples.acmerico.web;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCardApplication;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.CreditCardAppService;
import org.springframework.samples.acmerico.web.CreditCardAppController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.FilterType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.time.LocalDateTime;



@WebMvcTest(controllers = CreditCardAppController.class,
     excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
     classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class CreditCardApplicationControllerTest{

    private static final Integer TEST_BANK_ACCOUNT_ID = 1;
    private static final Integer TEST_CREDITCARDAPP_ID = 1;

    @MockBean
    private ClientService clientService;

    @MockBean
    private CreditCardAppService creditCardAppService;
    
    @MockBean
    private BankAccountService bankAccountService;

    @Autowired
    private MockMvc mockMvc;

    private Client client;
    
    private CreditCardApplication application;
    
    private BankAccount account;

    @BeforeEach
    void setup(){

        client = new Client();
        application = new CreditCardApplication();
        account = new BankAccount();

        final LocalDate bithday = LocalDate.of(1998, 11, 27);
        final LocalDate lEmpDate = LocalDate.of(2010, 01, 22);
        final LocalDateTime creationDate = LocalDateTime.of(2019, 11, 23, 12, 12, 12);


        client.setId(1);
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

        account.setId(TEST_BANK_ACCOUNT_ID);
        account.setAccountNumber("ES23 0025 0148 1259 1424");
        account.setAlias("Cuenta personal");
        account.setAmount(10000.);
        account.setCreatedAt(creationDate);
        account.setClient(client);

        application.setId(TEST_CREDITCARDAPP_ID);
        application.setStatus("PENDING");
        application.setBankAccount(account);
        application.setClient(client);

        when(creditCardAppService.findCreditCardAppById(TEST_CREDITCARDAPP_ID)).thenReturn(application);
    }

    @WithMockUser(value = "spring")
    @Test

    void testListSuccess() throws Exception{
        mockMvc.perform(get("/creditcardapps"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(model().attributeExists("credit_cards_app"))
        .andExpect(view().name("creditCardApps/creditCardAppList"));

    }

    @WithMockUser(value = "spring")
    @Test
    void testShowCreditCardApplicationSuccess() throws Exception{
        mockMvc.perform(get("/creditcardapps/{creditcardappsId}", TEST_CREDITCARDAPP_ID))
        .andExpect(model().attributeExists("credit_card_app"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("creditCardApps/creditCardAppDetails"));
        
        verify(creditCardAppService).findCreditCardAppById(TEST_CREDITCARDAPP_ID);
    }

    @WithMockUser(value = "spring")
    @Test
    void testAcceptCreditCardAppplication() throws Exception{
        mockMvc.perform(get("/creditcardapps/{creditcardappsId}/accept", TEST_CREDITCARDAPP_ID))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/creditcardapps"))
        .andExpect(status().is3xxRedirection());

    }

    @WithMockUser(value = "spring")
    @Test
    void testRefuseCreditCardAppplication() throws Exception{
        mockMvc.perform(get("/creditcardapps/{creditcardappsId}/refuse", TEST_CREDITCARDAPP_ID))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/creditcardapps"))
        .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(value = "spring")
    @Test

    void testShowClientCreditCards() throws Exception{
        mockMvc.perform(get("/mycreditcardapps"))
        .andExpect(model().hasNoErrors())
        .andExpect(status().is2xxSuccessful());

    }

    @WithMockUser(value = "spring")
    @Test
    void testRequestNewCreditCardHasErrors() throws Exception{
        mockMvc.perform(get("/creditcardapps/{bankAccountId}/new", TEST_BANK_ACCOUNT_ID))
        .andExpect(status().is3xxRedirection())
       .andExpect(view().name("redirect:/oups"));
       
       verify(bankAccountService).findBankAccountById(TEST_BANK_ACCOUNT_ID);

    }

    @WithMockUser(value = "spring")
    @Test
    void testRequestNewCreditCardSuccess() throws Exception{
        mockMvc.perform(get("/creditcardapps/{bankAccountId}/new", TEST_BANK_ACCOUNT_ID)
        .param("status", "PENDING")
        .with(csrf()));
        
       
       verify(bankAccountService).findBankAccountById(TEST_BANK_ACCOUNT_ID);

    }

    @WithMockUser(value = "spring")
    @Test
    void testRequestCreated() throws Exception{
        mockMvc.perform(get("/creditcardapps/created"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("creditCardApps/successfullyCreated"));
    }


    
}