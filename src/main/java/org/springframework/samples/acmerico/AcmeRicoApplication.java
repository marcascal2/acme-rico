package org.springframework.samples.acmerico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.samples.acmerico.apis.model.foreignExchange.Exchange;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication()
public class AcmeRicoApplication {
	
	private static final Logger log = LoggerFactory.getLogger(AcmeRicoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AcmeRicoApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Exchange exchange = restTemplate.getForObject(
					"https://api.exchangeratesapi.io/latest?base=", Exchange.class);
			log.info(exchange.toString());
		};
	}

}
