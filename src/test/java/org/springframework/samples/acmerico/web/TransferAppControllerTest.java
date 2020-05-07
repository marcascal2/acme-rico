package org.springframework.samples.acmerico.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.samples.acmerico.model.TransferApplication;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.TransferAppService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;

@WebMvcTest(controllers = TransferAppController.class,
				excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
				excludeAutoConfiguration = SecurityConfiguration.class)
public class TransferAppControllerTest {
	
	private static User user = new User();
	private static Client client = new Client();
	private static BankAccount bankAccountSource = new BankAccount();
	private static BankAccount bankAccountDestination = new BankAccount();
	private static TransferApplication transferApplication = new TransferApplication();
	
	@MockBean
	private TransferAppService transferAppService;

	@MockBean
	private BankAccountService accountService;
	
	@MockBean
	private ClientService clientService;
	
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
		client.setBankAccounts(Arrays.asList(bankAccountSource, bankAccountDestination));
		client.setTransferApps(Arrays.asList(transferApplication));
		
		bankAccountSource.setId(1);
		bankAccountSource.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccountSource.setAmount(10000.0);
		bankAccountSource.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccountSource.setAlias("Viajes");
		bankAccountSource.setClient(client);
		bankAccountSource.setTransfersApps(Arrays.asList(transferApplication));
		
		bankAccountDestination.setId(2);
		bankAccountDestination.setAccountNumber("ES44 4523 9853 3674 4366");
		bankAccountDestination.setAmount(10000.0);
		bankAccountDestination.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccountDestination.setAlias("Viajes");
		bankAccountDestination.setClient(client);
		
		transferApplication.setId(1);
		transferApplication.setStatus("PENDING");
		transferApplication.setAmount(1000.);
		transferApplication.setAccount_number_destination("ES44 4523 9853 3674 4366");
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListTransfersApp() throws Exception {
		when(this.transferAppService.findAllTransfersApplications()).thenReturn(Arrays.asList(transferApplication));
		
		mockMvc.perform(get("/transferapps"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transfers_app"))
			.andExpect(view().name("transfersApp/transferAppList"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListMineTransfersApp() throws Exception {
		when(this.clientService.findClientById(client.getId())).thenReturn(client);
		when(this.transferAppService.findAllTransfersApplicationsByClient(client)).thenReturn(Arrays.asList(transferApplication));
		
		mockMvc.perform(get("/transferapps_mine"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transfers_app"))
			.andExpect(view().name("transfersApp/transferAppList"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowTransferApplication() throws Exception {
		when(this.transferAppService.findTransferAppById(transferApplication.getId())).thenReturn(transferApplication);
		
		mockMvc.perform(get("/transferapps/{transferappsId}", transferApplication.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transfer_application"))
			.andExpect(view().name("transfersApp/transferAppDetails"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testCreateTransfers() throws Exception {
		when(this.accountService.findBankAccountById(bankAccountSource.getId())).thenReturn(bankAccountSource);
		
		mockMvc.perform(get("/transferapps/{bank_account_id}/new", bankAccountSource.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("transfer_app"))
			.andExpect(view().name("transfersApp/transfersAppCreate"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testSaveTransfersApplication() throws Exception {
		when(this.accountService.findBankAccountById(bankAccountSource.getId())).thenReturn(bankAccountSource);
		
		mockMvc.perform(post("/transferapps/{bank_account_id}/new", bankAccountSource.getId())
				.with(csrf())
				.param("amount", "1000")
				.param("account_number_destination", "ES44 4523 9853 3674 4366")
				.param("status", "PENDING"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/accounts"))
			.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testSaveTransfersApplicationHasErrors() throws Exception {
		when(this.accountService.findBankAccountById(bankAccountSource.getId())).thenReturn(bankAccountSource);
		
		mockMvc.perform(post("/transferapps/{bank_account_id}/new", bankAccountSource.getId())
				.with(csrf())
				.param("amount", "1000000000")
				.param("account_number_destination", "ES23 2323 2323 2323 2323")
				.param("status", "PENDING"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("transfersApp/transfersAppCreate"))
			.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
    @Test
    void testAcceptTransferApplication() throws Exception {
		when(this.transferAppService.findTransferAppById(transferApplication.getId())).thenReturn(transferApplication);
		
		mockMvc.perform(get("/transferapps/{transferappsId}/accept/{bankAccountId}", transferApplication.getId(), bankAccountSource.getId()))
		.andExpect(status().isFound())
		.andExpect(view().name("redirect:/transferapps"))
		.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testRefuseTransferApplication() throws Exception {
		when(this.transferAppService.findTransferAppById(transferApplication.getId())).thenReturn(transferApplication);
		
		mockMvc.perform(get("/transferapps/{transferappsId}/refuse/{bankAccountId}", transferApplication.getId(), bankAccountSource.getId()))
		.andExpect(status().isFound())
		.andExpect(view().name("redirect:/transferapps"))
		.andExpect(status().is3xxRedirection());
	}
    
}
