package org.springframework.samples.acmerico.validator;

import org.springframework.samples.acmerico.model.TransferApplication;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TransferAppValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		TransferApplication transferApp = (TransferApplication) obj;

		String destination = transferApp.getAccount_number_destination();
		Double amount = transferApp.getAmount();

		if (destination == null || destination.isEmpty()) {
			errors.rejectValue("account_number_destination", "Account number can not be blank",
					"Account number can not be blank");
		} else {
			if (!destination.matches("[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}")) {
				errors.rejectValue("account_number_destination",
						"Incorrect account pattern, e.g:ES32 2323 2323 2323 2323",
						"Incorrect account pattern, e.g:ES32 2323 2323 2323 2323");
			}
		}

		if (amount == null || amount == 0) {
			errors.rejectValue("amount", "Amount can not be blank", "Amount can not be blank");
		}

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return TransferApplication.class.isAssignableFrom(clazz);
	}

}
