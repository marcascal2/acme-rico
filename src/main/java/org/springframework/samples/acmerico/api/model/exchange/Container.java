package org.springframework.samples.acmerico.api.model.exchange;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Container {
	
	@NotBlank
	private String initRate;
	
	@NotBlank
	private String postRate;
	
	@NotNull
	private Double amount;
	
	private Double resultAmount;
	
}
