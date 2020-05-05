package org.springframework.samples.acmerico.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
//@TestPropertySource(locations = "classpath:application-mysql.properties")
public class DebtControllerIntegrationTest {
		
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(username="worker1",authorities= {"worker"})
    @Test
    void testListPendingDebtsSuccess() throws Exception{
		mockMvc.perform(get("/debts/pending"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("debts"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("debts/debtsList"));
		
	}

}
