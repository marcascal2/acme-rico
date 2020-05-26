package acmeRico

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU9 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.acme-rico.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.ico""", """.*.js""", """.*.jpg""", """.*.jpeg""", """.*.css""", """.*.less""", """.*.js@2.8.0"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.acme-rico.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("HU9")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(9)

		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(3)
		.exec(http("LoginForm")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "director1")
			.formParam("password", "director1")
			.formParam("_csrf", "8c34ba48-6c45-4359-afc7-6c196f7c891c"))
		.pause(10)

		.exec(http("LoanList")
			.get("/grantedLoans")
			.headers(headers_0))
		.pause(49)

		.exec(http("CreateNewLoan")
			.get("/grantedLoans/new?")
			.headers(headers_0))
		.pause(7)
		.exec(http("CreateNewLoanForm")
			.post("/grantedLoans/new")
			.headers(headers_3)
			.formParam("description", "This is a new loan")
			.formParam("minimum_amount", "1000")
			.formParam("minimum_income", "1000")
			.formParam("number_of_deadlines", "2")
			.formParam("opening_price", "1")
			.formParam("monthly_fee", "0.02")
			.formParam("single_loan", "Yes")
			.formParam("_csrf", "402544ab-ee27-4144-b539-6538def0c84d"))
		.pause(13)

	setUp(
		/*
		*Número mínimo de usuarios concurrentes no soportados por el sistema:
		*scn.inject(rampUsers(9500) during (100 seconds))
		*Número máximo de usuarios concurrentes soportados por el sistema con una buena experiencia de uso del servicio
		*scn.inject(rampUsers(18000) during (100 seconds))
		*/
		scn.inject(rampUsers(18000) during (100 seconds))
	).protocols(httpProtocol)
	 .assertions(
		 global.responseTime.max.lt(5000),
		 global.responseTime.mean.lt(1000),
		 global.successfulRequests.percent.gt(95)
	 )
}