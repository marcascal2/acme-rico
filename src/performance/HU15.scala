package acmerico

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU15 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(WhiteList(), BlackList(""".*.js""", """.*.png""", """.*.jpeg""", """.*.jpg""", """.*.css""", """.*.js@2.8.0""", """.*.ico"""))
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
		.pause(8)
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

	object ListAccountsClient {
		val listAccounts = exec(http("List accounts")
			.get("/accounts")
			.headers(headers_0))
		.pause(14)
	}

	object ShowAccountClient {
		val showAccount = exec(http("Show Account")
			.get("/accounts/1")
			.headers(headers_0))
		.pause(11)
	}

	object RequestTransferClient {
		val requestTransfer = exec(http("Request transfer")
			.get("/transferapps/1/new?")
			.headers(headers_0))
		.pause(11)
	}

	object SubmitTransferClient {
		val submitTransfer = exec(http("Submit transfer")
			.post("/transferapps/1/new")
			.headers(headers_2)
			.formParam("amount", "1000.0")
			.formParam("account_number_destination", "ES23 4938 2938 2394 2931")
			.formParam("status", "PENDING")
			.formParam("_csrf", "cb950a58-98c0-4149-bb74-0ff8729f64e9"))
	}

	val clientScn = scenario("Request a transfer as a client").exec(Home.home,
																	Login.login,
																	LoggedClient.logged,
																	ListAccountsClient.listAccounts,
																	ShowAccountClient.showAccount,
																	RequestTransferClient.requestTransfer,
																	SubmitTransferClient.submitTransfer)

	setUp(
		clientScn.inject(rampUsers(4000) during (100 seconds)),
	)
	.protocols(httpProtocol)
    .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}