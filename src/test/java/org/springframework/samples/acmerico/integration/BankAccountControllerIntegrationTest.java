package org.springframework.samples.acmerico.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.web.BankAccountController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountControllerIntegrationTest {
	
	private static final Integer TEST_CLIENT_ID = 1;
    private static final String TEST_BANK_ACCOUNT_NUMBER = "ES23 0025 0148 1259 1424";
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private BankAccountController bankAccountController;
	
	EntityManager entityManager;
	
//  @Test
//  void testShowClientAccounts() throws Exception{
//
//  	Client c = clientService.findClientById(TEST_CLIENT_ID);
//  	
//  	Model model = instanciaModel();
//  	Principal principal = instanciaPrincipal(c);
//  	
//  	String view = bankAccountController.showClientAccounts(principal, model);
//  	
//  	assertEquals(view, "accounts/accounts");
//  }
// 
//	@Test
//  void testInitCreationForm() throws Exception {
//  	ModelMap model=new ModelMap();
//      
//  	String view = bankAccountController.initCreationForm(TEST_CLIENT_ID, model);
//
//		assertEquals(view,"accounts/createAccountForm");
//		assertNotNull(model.get("bankAccount"));
//  }
 
//  @Test
//  void testProcessCreationFormSuccess() throws Exception{
//  	
//		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
//  	
//  	
//  	BankAccount bankAccount = new BankAccount();
//  	bankAccount.setAccountNumber(TEST_BANK_ACCOUNT_NUMBER);
//  	bankAccount.setAlias("Cuenta de ahorro");
//  	bankAccount.setAmount(1200.0);
//
//  	
//  	assertNotNull("bankAccount");
//
//  	String view = bankAccountController.processCreationForm(TEST_CLIENT_ID, bankAccount, bindingResult);
//  	
//  	assertEquals(view, "redirect:/accounts/");
//  	//Revisar controller: no redirige nunca al success
//  	
//  }
  
//  @Test
//  void testProcessCreationFormWithErrors() throws Exception {
//
//     
//     BankAccount bankAccount = new BankAccount();
//     bankAccount.setAccountNumber(TEST_BANK_ACCOUNT_NUMBER);
//     bankAccount.setAlias("Cuenta de banco personal");
//     bankAccount.setAmount(1200000.0);
//     
//     BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
//     bindingResult.reject("createdAt", "Requied!");
//     
//     
//     String view = bankAccountController.processCreationForm(TEST_CLIENT_ID, bankAccount, bindingResult);
//     
//     assertEquals(view,"accounts/createAccountForm");
//     
//
//  }
  
//  @Test
//  void testShowAccountInfo() throws Exception{
//  	
//  	ModelMap model = new ModelMap();
//  	
//  	String view = bankAccountController.showAccountInfo(TEST_BANK_ACCOUNT_ID, model);
//  	
//  	assertNotNull("noMoney");
//  	assertNotNull("bankAccount");
//  	assertEquals(view, "accounts/showAccountInfo");
//  }
//  
//  @Test
//  void testDeleteAccountError() throws Exception{
//  	
//  	Model model = instanciaModel();
//  	
//  	String view = bankAccountController.deleteAccount(TEST_BANK_ACCOUNT_ID, model);
//  	
//  	assertEquals(view, "accounts/showAccountInfo");
//  	
//  	//Lo mismo de antes con el caso positivo
//  }
//  
//  @Test
//  void testDepositMoney() throws Exception{
//  	
//  	ModelMap model = new ModelMap();
//  	String view = bankAccountController.depositMoney(TEST_BANK_ACCOUNT_ID, model);
//  	
//  	assertEquals(view, "accounts/depositMoney");
//  }
  
//  @Test
//  void testMakeDeposit() throws Exception{
//  	Client client = clientService.findClientById(TEST_CLIENT_ID);
//  	
//  	BankAccount bankAccount = new BankAccount();
//  	bankAccount.setAccountNumber(TEST_BANK_ACCOUNT_NUMBER);
//  	bankAccount.setAlias("Cuenta de ahorro");
//  	bankAccount.setAmount(1200.0);
//  	bankAccount.setCreatedAt(LocalDateTime.now().minusSeconds(2));
//  	bankAccount.setClient(client);
//  	
//  	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
//  	
//  	String view = bankAccountController.makeDeposit(TEST_BANK_ACCOUNT_ID, bankAccount, bindingResult);
//  	
//  	assertEquals(view, "redirect:/accounts/");
//  }
//  
  private Model instanciaModel() {
		return new Model() {
			
			@Override
			public Model mergeAttributes(Map<String, ?> attributes) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(String attributeName) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean containsAttribute(String attributeName) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Map<String, Object> asMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAttribute(String attributeName, Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAttribute(Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAllAttributes(Map<String, ?> attributes) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAllAttributes(Collection<?> attributeValues) {
				// TODO Auto-generated method stub
				return null;
			}
		};
  	
  }
  
  private Principal instanciaPrincipal(Client c) {
  	return new Principal() {
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return c.getUser().getUsername();
			}
		};
	}

}
