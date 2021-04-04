package uk.task.waracle.cakemanagement.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.task.waracle.cakemanagement.Exception.ResourceNotFoundException;
import uk.task.waracle.cakemanagement.rest.datatype.Cake;
import uk.task.waracle.cakemanagement.rest.datatype.CakesListResponse;
import uk.task.waracle.cakemanagement.service.CakeService;

@RestController
public class CakeController {

	private final CakeService cakeService;

	@Autowired
	// Constructor injection is better than the field injection, mock can be supplied
	// easily
	public CakeController(CakeService cakeService) {
		this.cakeService = cakeService;
	}

	/**
	 * @return Get all Cakes
	 */
	@GetMapping("/cakes")
	public CakesListResponse getAllCakes() {
		return cakeService.getAllCakes();
	}

	/**
	 * Creates the cake
	 *
	 * @param cake Cake instance to be saved
	 * @return Saved cake instance
	 */
	@PostMapping("/cake")
	public Cake createCake(@Validated @RequestBody Cake cake) {
		Cake createdCake = cakeService.createCake(cake);
		return createdCake;
	}

	/**
	 * Get Cake by Id
	 *
	 * @param cakeId Cake Id
	 * @return Cake Instances
	 * @throws ResourceNotFoundException For Cake is not available by Id
	 */
	@GetMapping("/cake/{id}")
	public ResponseEntity<Cake> getCakeById(@PathVariable(value = "id") long cakeId) throws ResourceNotFoundException {
		Cake cake = cakeService.getCakeById(cakeId);
		return ResponseEntity.ok().body(cake);
	}

	/**
	 * Update Cake
	 *
	 * @param cakeId      Cake Id
	 * @param cakeDetails Cake Details to be updated
	 * @return Updated Cake Instance
	 * @throws ResourceNotFoundException for CakeNotFound
	 */
	@PutMapping("/cake/{id}")
	public ResponseEntity<Cake> updateCakeDetails(@PathVariable(value = "id") long cakeId,
		@RequestBody Cake cakeDetails) throws ResourceNotFoundException {
		Cake cake = cakeService.updateCakeDetails(cakeId, cakeDetails);
		return ResponseEntity.ok().body(cake);
	}

	//delete cake
	@DeleteMapping("/cakes/{id}")
	public ResponseEntity<Object> deleteCake(@PathVariable(value = "id") long cakeId) throws ResourceNotFoundException {
		cakeService.deleteCake(cakeId);
		return ResponseEntity.ok().build();
	}

}
