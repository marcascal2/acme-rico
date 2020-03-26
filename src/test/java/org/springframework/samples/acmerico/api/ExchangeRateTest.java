package org.springframework.samples.acmerico.api;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.acmerico.api.controller.ExchangeController;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ExchangeController.class,
				excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
				excludeAutoConfiguration = SecurityConfiguration.class)
public class ExchangeRateTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "spring")
	@Test
	void testGetRates() throws Exception{
		mockMvc.perform(get("/exchanges"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("rates"))
			.andExpect(model().attributeExists("container"))
			.andExpect(view().name("exchanges/exchangeView"))
			.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@ParameterizedTest
	@ValueSource(strings = {"EUR", "USD", "CAD"})
	void testPostRatesSeveralInitRates(String initRate) throws Exception {
		mockMvc.perform(post("/exchanges")
				.with(csrf())
				.param("amount", "10.0")
				.param("initRate", initRate)
				.param("postRate", "EUR")
				.param("resultAmount", "0."))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("rates"))
			.andExpect(model().attributeExists("isPost"))
			.andExpect(view().name("exchanges/exchangeView"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@ParameterizedTest
	@ValueSource(strings = {"EUR", "USD", "CAD"})
	void testPostRatesSeveralPostRates(String postRate) throws Exception {
		mockMvc.perform(post("/exchanges")
				.with(csrf())
				.param("amount", "10.0")
				.param("initRate", "EUR")
				.param("postRate", postRate)
				.param("resultAmount", "0."))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("rates"))
			.andExpect(model().attributeExists("isPost"))
			.andExpect(view().name("exchanges/exchangeView"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testPostRatesNegative() throws Exception {
		mockMvc.perform(post("/exchanges")
				.with(csrf())
				.param("amount", "10.0")
				.param("resultAmount", "0."))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("container"))
			.andExpect(model().attributeHasFieldErrors("container", "initRate"))
			.andExpect(model().attributeHasFieldErrors("container", "postRate"))
			.andExpect(model().attributeExists("rates"))
			.andExpect(model().attributeExists("isPost"))
			.andExpect(view().name("exchanges/exchangeView"));
	}
	
}
