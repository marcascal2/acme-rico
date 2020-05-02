package org.springframework.samples.acmerico.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
//@TestPropertySource(
//  locations = "classpath:application-mysql.properties")
public class TransferAppControllerIntegrationTests {
	
	@Autowired
	private MockMvc mockMvc;	
	
	

	@WithMockUser(username="worker1",authorities= {"worker"})
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/transferapps"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("transfers_app"))
		.andExpect(view().name("transfersApp/transferAppList"))
		.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username="client1",authorities= {"client"})
    @Test
    void testListMineTransfersApp() throws Exception {
		mockMvc.perform(get("/transferapps_mine/{clientId}", 1))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transfers_app"))
			.andExpect(view().name("transfersApp/transferAppList"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username="worker1",authorities= {"worker"})
    @Test
    void testShowTransferApplication() throws Exception {		
		mockMvc.perform(get("/transferapps/{transferappsId}", 1))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transfer_application"))
			.andExpect(view().name("transfersApp/transferAppDetails"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username="client1",authorities= {"client"})
    @Test
    void testCreateTransfers() throws Exception {		
		mockMvc.perform(get("/transferapps/{bank_account_id}/new", 1))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transfer_app"))
			.andExpect(view().name("transfersApp/transfersAppCreate"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username="client1",authorities= {"client"})
    @Test
    void testSaveTransfersApplication() throws Exception {		
		mockMvc.perform(post("/transferapps/{bank_account_id}/new", 1)
				.with(csrf())
				.param("amount", "1000")
				.param("account_number_destination", "ES44 4523 9853 3674 4366")
				.param("status", "PENDING"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/accounts"))
			.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(username="client1",authorities= {"client"})
    @Test
    void testSaveTransfersApplicationHasErrors() throws Exception {		
		mockMvc.perform(post("/transferapps/{bank_account_id}/new", 1)
				.with(csrf())
				.param("amount", "1000000000")
				.param("account_number_destination", "ES23 2323 2323 2323 2323")
				.param("status", "PENDING"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("transfersApp/transfersAppCreate"))
			.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username="worker1",authorities= {"worker"})
    @Test
    void testAcceptTransferApplication() throws Exception {		
		mockMvc.perform(get("/transferapps/{transferappsId}/accept/{bankAccountId}", 1, 1))
		.andExpect(status().isFound())
		.andExpect(view().name("redirect:/transferapps"))
		.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(username="worker1",authorities= {"worker"})
    @Test
    void testRefuseTransferApplication() throws Exception {
		mockMvc.perform(get("/transferapps/{transferappsId}/refuse/{bankAccountId}", 1, 1))
		.andExpect(status().isFound())
		.andExpect(view().name("redirect:/transferapps"))
		.andExpect(status().is3xxRedirection());
	}

}
