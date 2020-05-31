package org.springframework.samples.acmerico.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DashboardHSQLTest {

	private static final String TEST_CLIENT_USERNAME = "client1";

	@Autowired
	private DashboardService dashboardService;

	@BeforeEach
	@DirtiesContext
	private void setUpData() {

	}

	@Test
	public void testAmountToPay() throws Exception {
		Map<String, List<Object>> map = dashboardService.amountsToPay(TEST_CLIENT_USERNAME);
		assertEquals(2, map.keySet().size());
	}

	@Test
	public void testApplicationsStatus() throws Exception {
		Map<String, List<Integer>> result = dashboardService.applicationsStatus(TEST_CLIENT_USERNAME);
		assertEquals(3, result.keySet().size());
	}

	@Test
	public void testMoneyPie() throws Exception {
		Map<String, Double> result = dashboardService.moneyDebtPie(TEST_CLIENT_USERNAME);
		assertEquals(2, result.keySet().size());
	}
	
	@Test
	public void testMyMoneyPie() throws Exception {
		Map<String, List<Object>> result  = dashboardService.myMoneyPie(TEST_CLIENT_USERNAME);
		assertEquals(2, result.keySet().size());

	}

}
