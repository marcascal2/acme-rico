package org.springframework.samples.acmerico.web;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.samples.acmerico.service.DebtService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DebtController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
public class DebtControllerTest {
	
	@MockBean
	private DebtService debtService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeAll
	static void setup() {
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListPendingDebtsSuccess() throws Exception{
		mockMvc.perform(get("/debts/pending"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("debts"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("debts/debtsList"));
		
		verify(debtService).getNonPayedDebts();
	}

}
