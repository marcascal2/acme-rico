package org.springframework.samples.petclinic.service;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TransfersTest {
	
//	@Autowired
//	private TransferService service;
//	
//	@Test
//	public void testCountTransfers() {
////		int count = this.service.count();
//		//assertEquals(count, 4);
//		
//	}

}
