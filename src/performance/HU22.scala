package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU22 extends Simulation {

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
		).pause(3)
		.exec(
		http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "director1")
			.formParam("password", "director1")
			.formParam("_csrf", "${stoken}")
		).pause(3)
  	}

	object FindClientsForm {
		val findClientsForm = exec(http("FindClientsForm")
			.get("/clients/find")
			.headers(headers_0))
		.pause(1)
	}

	object ShowClients {
		val showClients = exec(http("ShowClients")
			.get("/clients?lastName=")
			.headers(headers_0))
		.pause(2)
	}

	object ShowClient {
		val showClient = exec(http("ShowClient")
			.get("/clients/1")
			.headers(headers_0))
		.pause(1)
	}

	object ShowClientStatistics {
		val showClientStatistics = exec(http("ShowClientStatistics")
			.get("/dashboard/1/statics")
			.headers(headers_0))
	}

	val scn = scenario("HU22").exec(Home.home,
		Login.login,
		FindClientsForm.findClientsForm,
		ShowClients.showClients,
		ShowClient.showClient,
		ShowClientStatistics.showClientStatistics)

	setUp(
		scn.inject(rampUsers(8900) during (100 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}