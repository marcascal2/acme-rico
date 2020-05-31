package org.springframework.samples.acmerico.validator;

import java.time.LocalDate;

import org.springframework.samples.acmerico.model.Client;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

public class ClientValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		
		Client client = (Client) obj;
		String fn = client.getFirstName();
		String ln = client.getLastName();
		String add = client.getAddress();
		LocalDate bd = client.getBirthDate();
		String city = client.getCity();
		String ms = client.getMaritalStatus();
		Double spy = client.getSalaryPerYear();
		Integer age = client.getAge();
		String job = client.getJob();
		String un = client.getUser().getUsername();
		String pass = client.getUser().getPassword();
		MultipartFile dni = client.getDniFile();

		if (fn == null || fn.equals("")) {
			errors.rejectValue("firstName", "First name must not be empty", "First name must not be empty");
		}

		if (ln == null || ln.equals("")) {
			errors.rejectValue("lastName", "Last name must not be empty", "Last name must not be empty");
		}

		if (add == null || add.equals("")) {
			errors.rejectValue("address", "Address must not be empty", "Address must not be empty");
		}

		if (bd == null) {
			errors.rejectValue("birthDate", "Birth date must not be null", "Birth date must not be null");
		}

		if (city == null || city.equals("")) {
			errors.rejectValue("city", "City must not be empty", "City must not be empty");
		}

		if (ms == null || ms.equals("")) {
			errors.rejectValue("maritalStatus", "Marital status must not be empty", "Marital status must not be empty");
		}

		if (spy == null) {
			errors.rejectValue("salaryPerYear", "Salary per year must not be null", "Salary per year must not be null");
		}

		if (age == null) {
			errors.rejectValue("age", "Age must not be null", "Age must not be null");
		}

		if (job == null || job.equals("")) {
			errors.rejectValue("job", "Job must not be empty", "Job must not be empty");
		}

		if (un == null || un.equals("")) {
			errors.rejectValue("user.username", "Username must not be empty", "Username must not be empty");
		}

		if (pass == null || pass.equals("")) {
			errors.rejectValue("user.password", "Password must not be empty", "Password must not be empty");
		}

		if (dni.isEmpty()) {
			return;
		} else {
			if (!dni.getContentType().equals("image/jpeg")) {
				errors.rejectValue("dniFile", "DNI file must be jpg type", "DNI file must be jpg type");
			}
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Client.class.isAssignableFrom(clazz);
	}

}
