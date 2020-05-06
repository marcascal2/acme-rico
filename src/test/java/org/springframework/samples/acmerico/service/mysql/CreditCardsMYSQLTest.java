package org.springframework.samples.acmerico.service.mysql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.service.CreditCardService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class CreditCardsMYSQLTest {

	@Autowired
	private CreditCardService creditCardService;

	@Test
	public void testCountCreditCardAfterCreating() {
		List<CreditCard> cards = (List<CreditCard>) this.creditCardService.findCreditCards();
		assertThat(cards.size()).isEqualTo(4);
	}

	@Test
	public void testDeleteCreditCard() {
		this.creditCardService.deleteCreditCardById(1);
		Collection<CreditCard> cards = this.creditCardService.findCreditCards();
		assertThat(cards.size()).isEqualTo(3);
	}

}
