package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU8 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.ico""", """.*.png""", """.*.jpg""", """.*.jpeg""", """.*.css""", """.*.js""",""".*.js@2.8.0"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")


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

  object Exchanges{
	  val exchanges = exec(http("exchanges")
			.get("/exchanges"))
		.pause(20)
		// exchanges
		.exec(http("exchanges_done")
			.post("/exchanges")
			.headers(headers_2)
			.formParam("initRate", "NOK")
			.formParam("postRate", "ZAR")
			.formParam("amount", "120")
			.formParam("_csrf", "00c7fdef-1175-4d95-ad0f-6d293d51bf45")).pause(18)
  }



	val scn = scenario("HU8").exec(Home.home, Login.login,
								 Exchanges.exchanges)
		
	setUp(
		scn.inject(rampUsers(6000) during (100 seconds)),
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
	 )
	 
}