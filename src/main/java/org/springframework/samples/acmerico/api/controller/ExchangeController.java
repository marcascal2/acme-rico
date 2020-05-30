package org.springframework.samples.acmerico.api.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.api.model.exchange.Container;
import org.springframework.samples.acmerico.api.service.ExchangeService;
import org.springframework.samples.acmerico.validator.ExchangeRateValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExchangeController {

	private ExchangeService exchangeService;

	private static final String VIEWS_EXCHANGE = "exchanges/exchangeView";

	@Autowired
	public ExchangeController(ExchangeService exchangeService) {
		this.exchangeService = exchangeService;
	}

	@InitBinder("container")
	public void initContainerBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ExchangeRateValidator());
	}

	@GetMapping(value = "/exchanges")
	public String getRates(Model model) {
		Container container = new Container();
		List<String> rates = this.exchangeService.cargaLista();
		
		model.addAttribute("container", container);
		model.addAttribute("rates", rates);

		return VIEWS_EXCHANGE;
	}

	@PostMapping(value = "/exchanges")
	public String postRates(@ModelAttribute("container") @Valid Container container, BindingResult result,
			Model model) {
		String initRate = container.getInitRate();
		String postRate = container.getPostRate();
		Double amount = container.getAmount();

		Double resultAmount = this.exchangeService.calcularResultAmount(initRate, postRate, amount);
		container.setResultAmount(resultAmount);

		List<String> rates = this.exchangeService.cargaLista();

		model.addAttribute("rates", rates);
		model.addAttribute("isPost", initRate != null && postRate != null && amount != null);

		return VIEWS_EXCHANGE;
	}

}
