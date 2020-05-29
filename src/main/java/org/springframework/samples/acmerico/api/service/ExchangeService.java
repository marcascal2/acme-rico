package org.springframework.samples.acmerico.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.api.model.foreignExchange.Exchange;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {

	private String uri = "https://api.exchangeratesapi.io/latest?base=";
	private Exchange exchange;

	@Autowired
	private RestTemplate restTemplate;

	public List<String> cargaLista() {
		String url = uri + "EUR";
		exchange = restTemplate.getForObject(url, Exchange.class);
		List<String> rates = exchange.getRates().getAdditionalProperties().keySet().stream()
				.collect(Collectors.toList());
		rates.add("EUR");
		return rates;
	}

	public Double calcularResultAmount(String initRate, String postRate, Double amount) {
		String url;
		String empty = "";
		if (initRate == null || initRate.equals(empty)) {
			url = uri + "EUR";
		} else {
			url = uri + initRate;
		}

		exchange = restTemplate.getForObject(url, Exchange.class);

		Double iRate = 0.;
		Double pRate = 0.;
		Double resultAmount = 0.;

		if (initRate != null && postRate != null) {
			if (initRate.equals("EUR")) {
				iRate = 1.;
				if (postRate.equals("EUR")) {
					pRate = 1.;
					resultAmount = amount;
				} else {
					pRate = (Double) exchange.getRates().getAdditionalProperties().get(postRate);
					resultAmount = amount * pRate;
				}
			} else if (initRate != null && postRate != null && amount != null) {
				iRate = (Double) exchange.getRates().getAdditionalProperties().get(initRate);
				pRate = (Double) exchange.getRates().getAdditionalProperties().get(postRate);
				resultAmount = (amount * pRate) / iRate;
			}
		}

		resultAmount = Math.round(resultAmount * 100.0) / 100.0;
		return resultAmount;
	}

}
