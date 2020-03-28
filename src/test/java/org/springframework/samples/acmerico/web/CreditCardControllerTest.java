package org.springframework.samples.acmerico.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.CreditCardService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CreditCardController.class,
			    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			    excludeAutoConfiguration = SecurityConfiguration.class)
public class CreditCardControllerTest {
	
	private static User user = new User();
	private static Client client = new Client();
	private static BankAccount bankAccount = new BankAccount();
	private static CreditCard creditCard = new CreditCard();
    
	@MockBean
	private ClientService clientService;
	
	@MockBean
	private CreditCardService creditCardService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeAll
	static void setUp() {	
		user.setUsername("userPrueba");
		user.setPassword("userPrueba");
		user.setEnabled(true);
		
		client.setId(1);
		client.setFirstName("Germán");
		client.setLastName("Márquez Trujillo");
		client.setAddress("C/ Marques de Aracena, 37");
		client.setBirthDate(LocalDate.parse("1998-04-15"));
		client.setCity("Sevilla");
		client.setMaritalStatus("Single");
		client.setSalaryPerYear(2000.0);
		client.setAge(21);
		client.setJob("DP2 Developement Student");
		client.setLastEmployDate(LocalDate.parse("2019-04-15"));
		client.setUser(user);
		client.setBankAccounts(Arrays.asList(bankAccount));
		client.setCreditCards(Arrays.asList(creditCard));
		
		bankAccount.setId(1);
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);
		bankAccount.setCreditCards(Arrays.asList(creditCard));
		
		creditCard.setId(1);
		creditCard.setNumber("5130218133680652");
		creditCard.setDeadline("07/2022");
		creditCard.setCvv("156");
	}

	@WithMockUser(username = "userPrueba", roles = {"client"})
    @Test
    void testShowClientCards() throws Exception{
		when(this.clientService.findClientByUserName("userPrueba")).thenReturn(client);
		when(this.clientService.findCreditCardsByUsername("userPrueba")).thenReturn(Arrays.asList(creditCard));
		
		mockMvc.perform(get("/cards"))
		   .andExpect(status().isOk())
		   .andExpect(model().attributeExists("cards"))
		   .andExpect(model().attributeExists("clientId"))
		   .andExpect(view().name("cards/cards"))
		   .andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testAccountInfo() throws Exception{
		when(this.creditCardService.findCreditCardById(creditCard.getId())).thenReturn(creditCard);  
		
		mockMvc.perform(get("/cards/{cardId}/show", creditCard.getId()))
		   .andExpect(status().isOk())
		   .andExpect(model().attributeExists("creditCard"))
		   .andExpect(model().attribute("creditCard", hasProperty("number", is("5130218133680652"))))
		   .andExpect(model().attribute("creditCard", hasProperty("deadline", is("07/2022"))))
		   .andExpect(model().attribute("creditCard", hasProperty("cvv", is("156"))))
		   .andExpect(view().name("cards/showCardInfo"))
		   .andExpect(status().is2xxSuccessful());
	}
	
}
