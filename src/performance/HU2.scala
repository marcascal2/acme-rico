package acmerico

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU2 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.png""", """.*.jpeg""", """.*.jpg""", """.*.js@2.8.0"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-GB,en;q=0.9,es-ES;q=0.8,es;q=0.7,en-US;q=0.6")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Pragma" -> "no-cache",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "*/*",
		"Origin" -> "http://www.dp2.com",
		"Pragma" -> "no-cache",
		"Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_5 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_6 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)
	}

	object Login {
		val login = exec(http("Login")
			.post("/login")
			.headers(headers_6)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "2b1835ad-03f9-4b2a-9d2a-d922f1325332"))
		.pause(10)
	}

	object Logged {
		val logged = exec(http("Logged")
			.get("/personalData/client1")
			.headers(headers_4))
		.pause(2)
	}

	object ShowEditForm {
		val showEditForm = exec(http("Show edit form")
			.get("/personalData/1/edit")
			.headers(headers_4))
		.pause(10)
	}

	object EditPersonalData {
		val editPersonalData = exec(http("NewOwnerCreated")
			.post("/personalData/1/edit")
			.headers(headers_6)
			.formParam("firstName", "Pepe")
			.formParam("lastName", "Franklin")
			.formParam("address", "110 W. Liberty St.")
			.formParam("birthDate", "1998/04/04")
			.formParam("city", "Madison")
			.formParam("maritalStatus", "MARRIED")
			.formParam("salaryPerYear", "10000.0")
			.formParam("age", "23")
			.formParam("job", "WORKER")
			.formParam("lastEmployDate", "2018/06/06")
			.formParam("user.username", "client1")
			.formParam("user.password", "client1")
			.formParam("_csrf", "a5cce598-af28-49f8-b67a-412d939ac3d7")
		).pause(10)
	}

	val clientScn = scenario("HU2").exec(Home.home,
								   Login.login,
								   Logged.logged,
								   ShowEditForm.showEditForm,
								   EditPersonalData.editPersonalData)
		

	setUp(clientScn.inject(rampUsers(9000) during (100 seconds)))
	.protocols(httpProtocol)
    .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}