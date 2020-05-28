package org.springframework.samples.acmerico.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
	
	private ClientRepository clientRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}	

	@Transactional(readOnly = true)
	public Client findClientById(int id) throws DataAccessException {
		return clientRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Client findClientByUserName(String name) throws DataAccessException {
		return clientRepository.findByUserName(name);
	}

	@Transactional(readOnly = true)
	public Collection<Client> findClientByLastName(String lastName) throws DataAccessException {
		return clientRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveClient(Client client) throws DataAccessException {
		//creating client
		clientRepository.save(client);		
		//creating user
		userService.saveUser(client.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(client.getUser().getUsername(), "client");
	}

	public Collection<BankAccount> findBankAccountsByUsername(String username) {
		Client client = findClientByUserName(username);
		return client.getBankAccounts();
	}

	@Cacheable("myCreditCards")
	@Transactional(readOnly = true)
	public Collection<CreditCard> findCreditCardsByUsername(String username) {
		Client client = findClientByUserName(username);
		return client.getCreditCards();
	}
}
