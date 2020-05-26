package acmeRico

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU1 extends Simulation {

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
		"Content-Type" -> "multipart/form-data; boundary=----WebKitFormBoundaryAB8vq1PJHz61ewcE",
		"Origin" -> "http://www.acme-rico.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Origin" -> "http://www.acme-rico.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("HU1")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(5)

		.exec(http("Register")
			.get("/users/new")
			.headers(headers_0))
		.pause(14)
		.exec(http("RegisterForm")
			.post("/users/new")
			.headers(headers_2)
			//Dentro de la carpeta user-files/resources/ debe crearse el directorio 'acmeRico/hu1/'
			//y añadir el archivo 'RegisterForm.dat' que se encuentra dentro del directorio 'src/performance/data/' del proyecto
			.body(RawFileBody("acmeRico/hu1/RegisterForm.dat")))
		.pause(13)

		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_4")
			.get("/login")
			.headers(headers_4)))
		.pause(7)
		.exec(http("LoginForm")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "client123")
			.formParam("password", "client123")
			.formParam("_csrf", "ef440918-4fdc-4f45-a6c1-97628929d381"))
		.pause(10)

	setUp(
		/*
		*Número mínimo de usuarios concurrentes no soportados por el sistema:
		*scn.inject(rampUsers(11250) during (100 seconds))
		*Número máximo de usuarios concurrentes soportados por el sistema con una buena experiencia de uso del servicio
		*scn.inject(rampUsers(13000) during (100 seconds))
		*/
		scn.inject(rampUsers(13000) during (100 seconds))
	).protocols(httpProtocol)
	 .assertions(
		 global.responseTime.max.lt(5000),
		 global.responseTime.mean.lt(1000),
		 global.successfulRequests.percent.gt(95)
	 )
}