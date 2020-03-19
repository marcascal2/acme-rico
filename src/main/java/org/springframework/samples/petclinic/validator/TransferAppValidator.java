package org.springframework.samples.petclinic.validator;

import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TransferAppValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		TransferApplication transferApp = (TransferApplication) obj;
		
		String destination = transferApp.getAccount_number_destination();
		Double amount = transferApp.getAmount();
		
//		if (transferApp.getAmount() > transferApp.getBankAccount().getAmount()) {
//			errors.rejectValue("amount", "This amount can´t be higher than bank account amount", "This amount can´t be higher than bank account amount");
//		}
//
//		if (transferApp.getBankAccount().getAccountNumber().equals(transferApp.getAccount_number_destination())) {
//			errors.rejectValue("account_number_destination", "Account number can not be the same that destination number account", "Account number can not be the same that destination number account");
//		}
		
		if(destination == null || destination == "") {
			errors.rejectValue("account_number_destination", "Account number can not be blank", "Account number can not be blank");
		}
		
		if(amount == null) {
			errors.rejectValue("amount", "Amount can not be blank", "Amount can not be blank");
		}
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return TransferApplication.class.isAssignableFrom(clazz);
	}

}
