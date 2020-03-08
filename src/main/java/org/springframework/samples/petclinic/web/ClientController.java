package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {
	
	private static final String VIEWS_CLIENT_CREATE_OR_UPDATE_FORM = "clients/createOrUpdateClientForm";
	
	private final ClientService clientService;
	
	@Autowired
	public ClientController(ClientService clientService, UserService userService, AuthoritiesService authoritiesService) {
		this.clientService = clientService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/clients/new")
	public String initCreationForm(Map<String, Object> model) {
		Client client = new Client();
		model.put("client", client);
		return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/clients/new")
	public String processCreationForm(@Valid Client client, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating client, user and authorities
			this.clientService.saveClient(client);
			
			return "redirect:/clients/" + client.getId();
		}
	}

	@GetMapping(value = "/clients/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("client", new Client());
		return "clients/findClients";
	}

	@GetMapping(value = "/clients")
	public String processFindForm(Client client, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (client.getLastName() == null) {
			client.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Client> results = this.clientService.findClientByLastName(client.getLastName());
		if (results.isEmpty()) {
			// no clients found
			result.rejectValue("lastName", "notFound", "not found");
			return "clients/findClients";
		}
		else if (results.size() == 1) {
			// 1 client found
			client = results.iterator().next();
			return "redirect:/clients/" + client.getId();
		}
		else {
			// multiple clients found
			model.put("selections", results);
			return "clients/clientsList";
		}
	}

	@GetMapping(value = "/clients/{clientId}/edit")
	public String initUpdateClientForm(@PathVariable("clientId") int clientId, Model model) {
		Client client = this.clientService.findClientById(clientId);
		model.addAttribute(client);
		return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/clients/{clientId}/edit")
	public String processUpdateClientForm(@Valid Client client, BindingResult result,
			@PathVariable("clientId") int clientId) {
		if (result.hasErrors()) {
			return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
		}
		else {
			client.setId(clientId);
			this.clientService.saveClient(client);
			return "redirect:/clients/{clientId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/clients/{clientId}")
	public ModelAndView showClient(@PathVariable("clientId") int clientId) {
		ModelAndView mav = new ModelAndView("clients/clientsDetails");
		mav.addObject(this.clientService.findClientById(clientId));
		return mav;
	}

}