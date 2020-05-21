package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU5 extends Simulation {

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

	object FindEmployees {
		val findEmployees = exec(http("FindEmployees")
			.get("/employees/find")
			.headers(headers_0))
		.pause(1)
	}

	object NewEmployee {
		val newEmployee = exec(http("NewEmployeeForm")
			.get("/employees/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(6)
		.exec(http("NewEmployeeAdded")
			.post("/employees/new")
			.headers(headers_3)
			.formParam("firstName", "George")
			.formParam("lastName", "Dunphy")
			.formParam("salary", "2000.00")
			.formParam("user.username", "worker")
			.formParam("user.password", "worker")
			.formParam("_csrf", "${stoken}"))
	}

	object ListEmployees {
		val listEmployees = exec(http("ListEmployees")
			.get("/employees?lastName=")
			.headers(headers_0))
		.pause(1)
	}

	object ShowEmployee {
		val showEmployee = exec(http("ShowEmployee")
			.get("/employees/4")
			.headers(headers_0))
		.pause(1)
	}

	object DeleteEmployee {
		val deleteEmployee = exec(http("DeleteEmployee")
			.get("/employees/4/delete")
			.headers(headers_0))
		.pause(1)
	}

	val newEmployeeScn = scenario("NewEmployee").exec(
		Home.home,
		Login.login,
		FindEmployees.findEmployees,
		NewEmployee.newEmployee
	)
		
	val deleteEmployeeScn = scenario("DeleteEmployee").exec(
		Home.home,
		Login.login,
		FindEmployees.findEmployees,
		ListEmployees.listEmployees,
		ShowEmployee.showEmployee,
		DeleteEmployee.deleteEmployee
	)

	setUp(
		newEmployeeScn.inject(rampUsers(2250) during (100 seconds)),
		deleteEmployeeScn.inject(rampUsers(2250) during (100 seconds))
//		newEmployeeScn.inject(rampUsers(5000) during (100 seconds)),
//		deleteEmployeeScn.inject(rampUsers(5000) during (100 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}