package org.springframework.samples.petclinic.validator;

import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TransferAppValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		TransferApplication transferApp = (TransferApplication) obj;
		
		// Validations
		if (transferApp.getAmount() > transferApp.getBankAccount().getAmount()) {
			errors.rejectValue("amount", "This amount can´t be higher than bank account amount", "This amount can´t be higher than bank account amount");
		}

		if (transferApp.getBankAccount().getAccountNumber().equals(transferApp.getAccount_number_destination())) {
			errors.rejectValue("account_number_destination", "Account number can not be the same that destination number account", "Account number can not be the same that destination number account");
		}
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return TransferApplication.class.isAssignableFrom(clazz);
	}

}
