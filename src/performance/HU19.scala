package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU19 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.ico""", """.*.js""", """.*.js@2.8.0""", """.*.css""", """.*.png""", """.*.jpg""", """.*.jpeg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-GB,es;q=0.9,en-GB;q=0.8,en;q=0.7,es-419;q=0.6")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
	}

	object Login {
		val login = exec(http("LoginForm")
			.get("/login")
			.headers(headers_2)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(11)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	object ListBankAccounts {
		val listBankAccounts = exec(http("ListBankAccounts")
			.get("/accounts")
			.headers(headers_0))
		.pause(10)
	}

	object ShowBankAccount {
		val showBankAccount = exec(http("ShowBankAccount")
			.get("/accounts/1")
			.headers(headers_0))
		.pause(9)
	}

	object RequestCreditCard {
		val requestCreditCard = exec(http("RequestCreditCard")
			.get("/creditcardapps/1/new?")
			.headers(headers_0))
		.pause(7)
	}

	val scn = scenario("HU19").exec(
		Home.home,
		Login.login,
		ListBankAccounts.listBankAccounts,
		ShowBankAccount.showBankAccount,
		RequestCreditCard.requestCreditCard
	)

	setUp(
		scn.inject(rampUsers(4200) during (100 seconds)))
//		scn.inject(rampUsers(9000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}