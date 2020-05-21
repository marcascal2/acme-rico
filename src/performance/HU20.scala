package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU20 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.ico""", """.*.png""",
			 """.*.css""", """.*.js""",""".*.js@2.8.0""", """.*.jpg""", """.*.jpeg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:76.0) Gecko/20100101 Firefox/76.0")


	object Home {
		val home = exec(http("Home")
			.get("/"))
	
	}
	
	object Login {
    val login = exec(
      http("Login")
        .get("/login")
    ).pause(20)
    .exec(
      http("Logged")
        .post("/login")
        .formParam("username", "client1")
		.formParam("password", "client1")
		.formParam("_csrf", "ad866635-7393-435a-bba5-d99868e89975"))
    }

	object LoanAppsInfo{
		val loanAppList = exec(
			http("loanapps_list")
			.get("/myloanapps"))
		.pause(12)
		// loanapps_list
		.exec(http("loan_information")
			.get("/loanapps/3"))
		.pause(16)
		// loan_information
	}


	val scn = scenario("HU20").exec(Home.home, Login.login,
								 LoanAppsInfo.loanAppList)
	setUp(
		scn.inject(rampUsers(8000) during (100 seconds)),
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
	 )
}