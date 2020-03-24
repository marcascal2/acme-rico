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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.validation.Valid;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_CLIENT_CREATE_FORM = "users/createClientForm";

	private final ClientService clientService;

	@Autowired
	public UserController(ClientService clinicService) {
		this.clientService = clinicService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
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
			MultipartFile file = client.getDniFile();
			if (result.hasErrors()) {
				return VIEWS_CLIENT_CREATE_FORM;
			}
			else {
				InputStream dni = new ByteArrayInputStream(file.getBytes());
				//creating owner, user, and authority
				this.clientService.saveClient(client);
				DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
				DbxClientV2 dropboxClient = new DbxClientV2(config, "vpE6YdhjRO0AAAAAAAAAx2xh4rUC8VL_ZU9UCMvI0nVN8K_rFGVq6a9omN2yd4a5");
				dropboxClient.files().uploadBuilder("/" + client.getFirstName() + " " + client.getLastName() + ".jpg").uploadAndFinish(dni);
				return "redirect:/";
			}
		} catch (Exception e) {
			return "redirect:/oups";
		}
	}
}
