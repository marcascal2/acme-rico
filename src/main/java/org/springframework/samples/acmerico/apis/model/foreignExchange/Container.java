package org.springframework.samples.acmerico.apis.model.foreignExchange;

import lombok.Data;

@Data
public class Container {
	
	private String initRate;
	
	private String postRate;
	
	private Double amount;
	
	private Double amountResult;
	
}
