package org.springframework.samples.acmerico.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
//@TestPropertySource(
//  locations = "classpath:application-mysql.properties")
public class CreditCardControllerIntegrationTests {

	private static final Integer TEST_CREDIT_CARD_ID = 1;
	
	@Autowired
	private MockMvc mockMvc;	

	@WithMockUser(username="client1",authorities= {"client"})
    @Test
	void testShowClientCards() throws Exception {
		mockMvc.perform(get("/cards")).andExpect(status().isOk())
			.andExpect(model().attributeExists("cards"))
			.andExpect(model().attributeExists("clientId"))
			.andExpect(view().name("cards/cards"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username="client1",authorities= {"client"})
	@Test
	@Disabled //TODO REVISAR CON MYSQL
	void testAccountInfo() throws Exception {
		mockMvc.perform(get("/cards/{cardId}/show", 1))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("creditCard"))
			.andExpect(model().attribute("creditCard", hasProperty("number", is("4095742744779740"))))
			.andExpect(model().attribute("creditCard", hasProperty("deadline", is("01/2021"))))
			.andExpect(model().attribute("creditCard", hasProperty("cvv", is("123"))))			
			.andExpect(view().name("cards/showCardInfo"))
			.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username="client1",authorities= {"client"})
	@Test
	void testDeleteSuccess() throws Exception {
		mockMvc.perform(get("/cards/{cardId}/delete", TEST_CREDIT_CARD_ID)).andExpect(status().isFound())
				.andExpect(view().name("redirect:/cards")).andExpect(status().is3xxRedirection());
	}
}
