package uk.task.waracle.cakemanagement.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.task.waracle.cakemanagement.Exception.ResourceNotFoundException;
import uk.task.waracle.cakemanagement.rest.datatype.Cake;
import uk.task.waracle.cakemanagement.rest.datatype.CakesListResponse;

import java.util.List;

@SpringBootTest
public class CakeServiceTest {

	@Autowired
	private CakeService cakeService;
	Cake cake;

	@BeforeEach
	public void setup() {

		cake = new Cake("ButterScotch", "WithEgg", 55);
	}

	@AfterEach
	public void after() {
		//Clean up all created cakes
		try {
			CakesListResponse wrapper = cakeService.getAllCakes();
			if (wrapper != null && wrapper.getCake() != null) {
				for (Cake cake : wrapper.getCake()) {
					cakeService.deleteCake(cake.getId());
				}
			}
		}
		catch (Exception ex) {

		}
	}

	@Test
	public void shouldCreateCake() {
		
		Cake savedCake = cakeService.createCake(cake);
		assertCake("ButterScotch", "WithEgg", 55, savedCake);
	}

	@Test
	public void shouldRetrieveAllCakes() {
		//Create two cakes
		for (int index = 0; index < 2; index++) {
			Cake cake = new Cake("ButterScotch" + index, "WithEgg" + index, 55 + index);
			cakeService.createCake(cake);
		}

		List<Cake> cakesList = cakeService.getAllCakes().getCake();
		Assert.assertEquals(2, cakesList.size());
		for (int index = 0; index < 2; index++) {
			assertCake("ButterScotch" + index, "WithEgg" + index, 55 + index, cakesList.get(index));
		}
	}

	@Test
	public void shouldUpdateCake() throws Exception {
		
		Cake savedCake = cakeService.createCake(cake);
		savedCake.setCakeType("WithFruits");
		Cake updatedCake = cakeService.updateCakeDetails(savedCake.getId(), savedCake);
		assertCake("ButterScotch", "WithFruits", 55, updatedCake);
	}

	@Test
	public void shouldDeleteACake() throws ResourceNotFoundException {
		
		Cake savedCake = cakeService.createCake(cake);
		cakeService.deleteCake(savedCake.getId());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> cakeService.deleteCake(savedCake.getId()));
	}

	private void assertCake(String cakeName, String cakeType, long price, Cake cake) {
		Assert.assertTrue(cake.getId() > 0);
		Assert.assertEquals(cakeName, cake.getCakeName());
		Assert.assertEquals(cakeType, cake.getCakeType());
		Assert.assertEquals(price, cake.getPrice());
	}
}
