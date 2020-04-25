//package org.springframework.samples.acmerico.e2e;
//
//import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
//import static org.hamcrest.Matchers.*;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import groovy.util.logging.Log;
//import io.restassured.authentication.FormAuthConfig;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
//@Log
//public class ExchangeControllerIntegrationTests {
//
//	@Test
//	public void testDefaultExchange() {
//		given().auth().form("client1", "client1", FormAuthConfig.springSecurity())
//		.with()
//		.header("Authentication","client")
//		.when()
//		.get("http://localhost:8080/exchanges")
//		.then().statusCode(200);
////	.assertThat()
////	.body("data.leagueId", equalTo(1))
////	.and()
////	.body("data.homeTeam",equalTo("Betis"));
//	}
//
//}
