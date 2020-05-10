package org.springframework.samples.acmerico.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.service.AuthoritiesService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private final UserService userService;

	@Autowired
	public ClientController(ClientService clientService, UserService userService,
			AuthoritiesService authoritiesService) {
		this.clientService = clientService;
		this.userService = userService;
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
		Boolean isRegistered = this.userService.usernameRepeated(client.getUser().getUsername());
		Boolean nullUsername = client.getUser().getUsername().equals(null) || client.getUser().getUsername().equals("");
		Boolean nullPassword = client.getUser().getPassword().equals(null) || client.getUser().getPassword().equals("");
		if (isRegistered) {
			result.rejectValue("user.username", "This username is already registered",
					"This username is already registered");
		}
		
		if(nullUsername) {
			result.rejectValue("user.username", "Username must not be empty", "Username must not be empty" );
		}
		
		if(nullPassword) {
			result.rejectValue("user.password", "Password must not be empty", "Password must not be empty" );
		}
		
		if (result.hasErrors()) {
			return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
		} else {
			// creating client, user and authorities
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
		} else if (results.size() == 1) {
			// 1 client found
			client = results.iterator().next();
			return "redirect:/clients/" + client.getId();
		} else {
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
		} else {
			client.setId(clientId);
			this.clientService.saveClient(client);
			return "redirect:/clients/{clientId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * 
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/clients/{clientId}")
	public ModelAndView showClient(@PathVariable("clientId") int clientId) {
		ModelAndView mav = new ModelAndView("clients/clientsDetails");
		mav.addObject(this.clientService.findClientById(clientId));
		return mav;
	}

	@GetMapping(value = "/personalData/{name}")
	public ModelAndView processInitPersonalDataForm(@PathVariable("name") String name, Model model) {
		ModelAndView mav = new ModelAndView("clients/clientsDetails");
		mav.addObject(this.clientService.findClientByUserName(name));
		return mav;
	}

	@GetMapping(value = "/personalData/{clientId}/edit")
	public String initUpdatepersonalDataForm(@PathVariable("clientId") int clientId, Model model) {
		Client client = this.clientService.findClientById(clientId);
		model.addAttribute(client);
		return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/personalData/{clientId}/edit")
	public String processUpdatePersonalDataForm(@Valid Client client, BindingResult result,
			@PathVariable("clientId") int clientId) {
		if (result.hasErrors()) {
			return VIEWS_CLIENT_CREATE_OR_UPDATE_FORM;
		} else {
			client.setId(clientId);
			this.clientService.saveClient(client);
			SecurityContextHolder.clearContext();
			return "redirect:/";
		}
	}

}
