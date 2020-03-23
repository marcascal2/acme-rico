package org.springframework.samples.acmerico.apis.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.apis.model.foreignExchange.Container;
import org.springframework.samples.acmerico.apis.model.foreignExchange.Exchange;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class ExchangeController {
	
	private Exchange exchange;
	private String uri = "https://api.exchangeratesapi.io/latest?base=";
	private List<String> rates = Arrays.asList("CAD","HKD","ISK","PHP","DKK","HUF","CZK","AUD","RON","SEK","IDR","INR","BRL","RUB","HRK","JPY","THB","CHF","SGD","PLN","BGN","TRY","CNY","NOK","NZD","ZAR","USD","MXN","ILS","GBP","KRW","MYR");
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	public ExchangeController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@RequestMapping(value = "/exchanges", method = RequestMethod.GET)
	public String getRates(Model model){		
		Container container = new Container();
		
		model.addAttribute("container", container);
		model.addAttribute("initRate", "");
		model.addAttribute("postRate", "");
		model.addAttribute("amount", 0.);
		model.addAttribute("resultAmount", 0.);
		model.addAttribute("rates", rates);
		
		return "exchanges/exchangeView";
	}
	
	@PostMapping(value = "/exchanges")
	public String postRates(@Valid Container container, Model model) {
		String initRate = container.getInitRate();
		String postRate = container.getPostRate();
		Double amount = container.getAmount();
		
		String url = uri + initRate;
		
		exchange = restTemplate.getForObject(url, Exchange.class);
		
		//Esto no coge los rates d elas monedas, saca nulls, hay que buscar alguna forma de cogerlos.
		Double iRate = (Double) exchange.getRates().getAdditionalProperties().get(initRate);
		Double pRate = (Double) exchange.getRates().getAdditionalProperties().get(postRate);

		Double res = (amount*pRate)/iRate;
		model.addAttribute("initRate", initRate);
		model.addAttribute("postRate", postRate);
		model.addAttribute("amount", amount);
		model.addAttribute("resultAmount", res);
		model.addAttribute("rates", rates);
		model.addAttribute("isPost", true);
		
		return "exchanges/exchangeView";
	}
	
}
