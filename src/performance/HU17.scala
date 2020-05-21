package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU17 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.png""", """.*.jpg""", """.*.ico""", """.*.jpeg""", """.*.js@2.8.0"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-GB,en-US;q=0.9,en;q=0.8,es;q=0.7")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(2)
	}

	object Login {
		val login = exec(
		http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_5")
			.get("/login")
			.headers(headers_1))
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(7)
		.exec(
		http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "client2")
			.formParam("password", "client2")
			.formParam("_csrf", "${stoken}")
		).pause(3)
  	}

	object ListCards {
		val listCards = exec(http("ListCards")
			.get("/cards")
			.headers(headers_0))
			.pause(1)
	}

	object ShowCard {
		val showCard = exec(http("ShowCard")
			.get("/cards/2/show")
			.headers(headers_0))
		.pause(1)
	}

	object DeleteCard {
		val deleteCard = exec(http("DeleteCard")
			.get("/cards/1/delete?")
			.headers(headers_0))
	}

	val scn = scenario("HU17").exec(Home.home,
		Login.login,
		ListCards.listCards,
		ShowCard.showCard,
		DeleteCard.deleteCard)
		
	setUp(scn.inject(rampUsers(7400) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}