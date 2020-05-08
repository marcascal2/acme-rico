package org.springframework.samples.acmerico.service.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DirtiesContext
public class DashboardMYSQLTest {

	private static final String TEST_CLIENT_USERNAME = "client1";

	@Autowired
	private DashboardService dashboardService;

	@Test
	public void testAmountToPay() throws Exception {
		Map<String, List<Object>> map = dashboardService.amountsToPay(TEST_CLIENT_USERNAME);
		assertEquals(map.keySet().size(), 2);
	}

	@Test
	public void testApplicationsStatus() throws Exception {
		Map<String, List<Integer>> result = dashboardService.applicationsStatus(TEST_CLIENT_USERNAME);
		assertEquals(result.keySet().size(), 3);
	}

	@Disabled
	@Test
	public void testMoneyDebtPie() throws Exception {
		Map<String, Double> result = dashboardService.moneyDebtPie(TEST_CLIENT_USERNAME);
		assertEquals(result.keySet().size(), 2);
	}
	@Test
	public void testMyMoneyPie() throws Exception {
		Map<String, List<Object>> result  = dashboardService.myMoneyPie(TEST_CLIENT_USERNAME);
		assertEquals(result.keySet().size(), 2);

	}

}