package acmeRico

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU16 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.acme-rico.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.ico""", """.*.js""", """.*.jpg""", """.*.jpeg""", """.*.css""", """.*.less""", """.*.js@2.8.0"""), WhiteList())
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.acme-rico.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "identity",
		"Proxy-Connection" -> "Keep-Alive",
		"Range" -> "bytes=0-2147483646",
		"User-Agent" -> "Microsoft BITS/7.8")

	val headers_10 = Map(
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive")

    val uri1 = "http://clientservices.googleapis.com/chrome-variations/seed"
    val uri2 = "http://blob.weather.microsoft.com/static/mws-new/WeatherImages/210x173/2.jpg"

	val scn = scenario("HU16")
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
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "71eb15e0-2793-458f-ac1b-24cd3c0e7e36"))
		.pause(10)

		.exec(http("BankAccounts")
			.get("/accounts")
			.headers(headers_0))
		.pause(15)

		.exec(http("MyBankAccount")
			.get("/accounts/1")
			.headers(headers_0))
		.pause(17)

		.exec(http("CreateInstantTransferApplication")
			.get("/transferapps/1/new?")
			.headers(headers_0))
		.pause(12)
		.exec(http("CreateInstantTransferApplicationForm")
			.post("/transferapps/1/new")
			.headers(headers_3)
			.formParam("amount", "50")
			.formParam("account_number_destination", "ES32 1578 1385 2050 0031")
			.formParam("status", "PENDING")
			.formParam("_csrf", "b9d36264-e3bd-4748-9ca5-6dc1eee81395"))
		.pause(51)

	setUp(
		/*
		*Número mínimo de usuarios concurrentes no soportados por el sistema:
		*scn.inject(rampUsers(8000) during (100 seconds))
		*Número máximo de usuarios concurrentes soportados por el sistema con una buena experiencia de uso del servicio
		*scn.inject(rampUsers(29000) during (100 seconds))
		*/
		scn.inject(rampUsers(29000) during (100 seconds))
	).protocols(httpProtocol)
	 .assertions(
		 global.responseTime.max.lt(5000),
		 global.responseTime.mean.lt(1000),
		 global.successfulRequests.percent.gt(95)
	 )
}