package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU6 extends Simulation {

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
		).pause(5)
		.exec(
		http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "director1")
			.formParam("password", "director1")
			.formParam("_csrf", "${stoken}")
		).pause(4)
  	}

	object ListLoans {
		val listLoans = exec(http("ListLoans")
			.get("/grantedLoans")
			.headers(headers_0))
		.pause(2)
	}

	object CreateLoan {
		val createLoan = exec(
			http("CreateLoan")
			.get("/grantedLoans/new?")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(16)
		.exec(http("CreatedLoan")
			.post("/grantedLoans/new")
			.headers(headers_2)
			.formParam("description", "prueba test loan")
			.formParam("minimum_amount", "200.0")
			.formParam("minimum_income", "600.0")
			.formParam("number_of_deadlines", "3")
			.formParam("opening_price", "120.0")
			.formParam("monthly_fee", "0.2")
			.formParam("single_loan", "Yes")
			.formParam("_csrf", "${stoken}"))
	}
	
	val scn = scenario("HU6").exec(Home.home,
		Login.login,
		ListLoans.listLoans,
		CreateLoan.createLoan)
		
	setUp(scn.inject(rampUsers(6800) during (100 seconds)) //Minimo de usuarios concurrentes que provoca saturacion en el sistema
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}