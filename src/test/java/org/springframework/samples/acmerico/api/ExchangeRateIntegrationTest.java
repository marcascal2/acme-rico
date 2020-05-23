package org.springframework.samples.acmerico.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class ExchangeRateIntegrationTest {

	@Test
	public void testExchangeRate() {
		when().get("https://api.exchangeratesapi.io/latest?base=EUR")
		.then().statusCode(200)
		.and().assertThat()
			.body("date", notNullValue())
			.body("base", equalTo("EUR"))
			.body("rates", hasKey("USD"))
			.body("rates", hasKey("CNY"));
	}
	
}
