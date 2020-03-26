package org.springframework.samples.acmerico.apis.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.apis.model.foreignExchange.Container;
import org.springframework.samples.acmerico.apis.model.foreignExchange.Exchange;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class ExchangeController {

	private Exchange exchange;
	private String uri = "https://api.exchangeratesapi.io/latest?base=";
	private List<String> rates;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	public ExchangeController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@RequestMapping(value = "/exchanges", method = RequestMethod.GET)
	public String getRates(Model model) {
		Container container = new Container();

		String url = uri + "EUR";

		exchange = restTemplate.getForObject(url, Exchange.class);
		rates = exchange.getRates().getAdditionalProperties().keySet().stream().collect(Collectors.toList());
		rates.add("EUR");
		
		model.addAttribute("container", container);
		model.addAttribute("rates", rates);

		return "exchanges/exchangeView";
	}

	@PostMapping(value = "/exchanges")
	public String postRates(@ModelAttribute("container") @Valid Container container, Model model) {
		String initRate = container.getInitRate();
		String postRate = container.getPostRate();
		Double amount = container.getAmount();

		String url = uri + initRate;

		exchange = restTemplate.getForObject(url, Exchange.class);
		rates = exchange.getRates().getAdditionalProperties().keySet().stream().collect(Collectors.toList());
		rates.add("EUR");
		
		Double iRate;
		Double pRate;
		if(initRate.equals("EUR")) {
			iRate = 1.;
			if(postRate.equals("EUR")) {
				pRate = 1.;
			}else {
				pRate = (Double) exchange.getRates().getAdditionalProperties().get(postRate);
			}
		}else {
			iRate = (Double) exchange.getRates().getAdditionalProperties().get(initRate);
			pRate = (Double) exchange.getRates().getAdditionalProperties().get(postRate);
		}

		Double resultAmount = (amount * pRate) / iRate;
		resultAmount = Math.round(resultAmount * 100.0) / 100.0;
		container.setResultAmount(resultAmount);

		model.addAttribute("rates", rates);
		model.addAttribute("isPost", true);

		return "exchanges/exchangeView";
	}

}
