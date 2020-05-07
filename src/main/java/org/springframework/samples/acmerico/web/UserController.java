/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.acmerico.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.api.service.DropboxService;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.UserService;
import org.springframework.samples.acmerico.validator.ClientValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

	private static final String VIEWS_CLIENT_CREATE_FORM = "users/createClientForm";

	private final ClientService clientService;
	
	private final UserService userService;
	
	private final DropboxService dropboxService;

	@Autowired
	public UserController(ClientService clinicService, DropboxService dropboxService, UserService userService) {
		this.clientService = clinicService;
		this.dropboxService = dropboxService;
		this.userService = userService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("client")
	public void initClientBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ClientValidator());
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		Client client = new Client();
		model.put("client", client);
		return VIEWS_CLIENT_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Client client, BindingResult result) {
		try {
			Boolean isRegistered = this.userService.usernameRepeated(client.getUser().getUsername());
			if (isRegistered) {
				result.rejectValue("user.username", "This username is already registered",
						"This username is already registered");
			}
			if (result.hasErrors()) {
				return VIEWS_CLIENT_CREATE_FORM;
			}
			else {
				//creating owner, user, and authority
				this.clientService.saveClient(client);
				if(!client.getDniFile().isEmpty()) {
					this.dropboxService.uploadFile(client.getDniFile(), client);
				}
				return "redirect:/";
			}
		} catch (Exception e) {
			return "redirect:/oups";
		}
	}
}
