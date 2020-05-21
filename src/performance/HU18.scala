package acmerico

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU18 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.png""", """.*.jpg""", """.*.jpeg""", """.*.js@2.8.0""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-es")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1 Safari/605.1.15")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(12)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0))
		.pause(9)
	}

	object LoggedClient {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "df1f1122-81a9-490a-803a-4555e563a320"))
		.pause(17)
	}

	object CreditCardApplications {
		val creditCardApplications = exec(http("Get credit card apps")
			.get("/mycreditcardapps")
			.headers(headers_0))
		.pause(9)
	}

	val scn = scenario("HU18").exec(Home.home,
									Login.login,
									LoggedClient.logged,
									CreditCardApplications.creditCardApplications)

	setUp(
		scn.inject(rampUsers(4000) during (100 seconds)),
	)
	.protocols(httpProtocol)
    .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}