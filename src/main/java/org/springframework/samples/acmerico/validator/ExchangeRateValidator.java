package org.springframework.samples.acmerico.validator;

import org.springframework.samples.acmerico.api.model.foreignExchange.Container;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ExchangeRateValidator implements Validator{

	@Override
	public void validate(Object obj, Errors errors) {
		Container c = (Container) obj;
		
		String iRate = c.getInitRate();
		String pRate = c.getPostRate();
		Double amount = c.getAmount();
		
		if(iRate == null || iRate == "") {
			errors.rejectValue("initRate", "Can not be blank", "Can not be blank");
		}
		
		if(pRate == null || pRate == "") {
			errors.rejectValue("postRate", "Can not be blank", "Can not be blank");
		}

		if(amount == null || amount < 0.){
			errors.rejectValue("amount", "Can not be null or negative", "Can not be null or negative");
		}
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Container.class.isAssignableFrom(clazz);
	}
}
