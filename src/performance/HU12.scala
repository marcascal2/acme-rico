package acmeRico

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU12 extends Simulation {

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



	val scn = scenario("HU12")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)

		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(5)
		.exec(http("LoginForm")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "director1")
			.formParam("password", "director1")
			.formParam("_csrf", "886c67fc-1364-44ee-8947-1e4583915bb6"))
		.pause(12)

		.exec(http("LoanList")
			.get("/grantedLoans")
			.headers(headers_0))
		.pause(9)

	setUp(
		/*
		*Número mínimo de usuarios concurrentes no soportados por el sistema:
		*scn.inject(rampUsers(26000) during (100 seconds))
		*Número máximo de usuarios concurrentes soportados por el sistema con una buena experiencia de uso del servicio
		*scn.inject(rampUsers(45000) during (100 seconds))
		*/
		scn.inject(rampUsers(45000) during (100 seconds))
	).protocols(httpProtocol)
	 .assertions(
		 global.responseTime.max.lt(5000),
		 global.responseTime.mean.lt(1000),
		 global.successfulRequests.percent.gt(95)
	 )
}