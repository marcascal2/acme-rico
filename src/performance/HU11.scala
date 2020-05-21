package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU11 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.ico""", """.*.png""", """.*.jpeg""", """.*.jpg""", """.*.js@2.8.0"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-GB,es;q=0.9,en-GB;q=0.8,en;q=0.7,es-419;q=0.6")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

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
			.formParam("username", "director1")
			.formParam("password", "director1")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	object ListLoanApps {
		val listLoanApps = exec(http("ListLoanApps")
			.get("/loanapps")
			.headers(headers_0))
		.pause(7)
	}

	object ShowLoanApp {
		val showLoanApp = exec(http("ShowLoanApp")
			.get("/loanapps/1")
			.headers(headers_0))
		.pause(8)
	}

	object AcceptLoanApp {
		val acceptLoanApp = exec(http("AcceptLoanApp")
			.get("/loanapps/1/accept?")
			.headers(headers_0))
		.pause(9)
	}

	object RefuseLoanApp {
		val refuseLoanApp = exec(http("RefuseLoanApp")
			.get("/loanapps/1/refuse?")
			.headers(headers_0))
		.pause(4)
	}

	val acceptLoanScn = scenario("AcceptLoan").exec(
		Home.home,
		Login.login,
		ListLoanApps.listLoanApps,
		ShowLoanApp.showLoanApp,
		AcceptLoanApp.acceptLoanApp
	)

	val refuseLoanScn = scenario("RefuseLoan").exec(
		Home.home,
		Login.login,
		ListLoanApps.listLoanApps,
		ShowLoanApp.showLoanApp,
		RefuseLoanApp.refuseLoanApp
	)

	setUp(
		acceptLoanScn.inject(rampUsers(2000) during (100 seconds)),
		refuseLoanScn.inject(rampUsers(2000) during (100 seconds))
//		acceptLoanScn.inject(rampUsers(5000) during (100 seconds)),
//		refuseLoanScn.inject(rampUsers(5000) during (100 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}