package org.springframework.samples.acmerico.validator;

import org.springframework.samples.acmerico.model.Client;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

public class ClientValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Client client = (Client) obj;
		MultipartFile dni = client.getDniFile();

		if (dni == null) {
			errors.rejectValue("dniFile", "DNI file can not be blank", "DNI file can not be blank");
		}

		if (dni != null && !dni.getContentType().equals("image/jpeg")) {
			errors.rejectValue("dniFile", "DNI file must be jpg type", "DNI file must be jpg type");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Client.class.isAssignableFrom(clazz);
	}

}
