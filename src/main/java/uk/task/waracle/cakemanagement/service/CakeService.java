package uk.task.waracle.cakemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.task.waracle.cakemanagement.CakeUtils;
import uk.task.waracle.cakemanagement.Exception.ResourceNotFoundException;
import uk.task.waracle.cakemanagement.model.CakeEntity;
import uk.task.waracle.cakemanagement.rest.datatype.Cake;
import uk.task.waracle.cakemanagement.rest.datatype.CakesListResponse;
import uk.task.waracle.cakemanagement.repository.CakeRepository;

import java.util.List;

import static uk.task.waracle.cakemanagement.CakeUtils.buildCakesList;

@Service
public class CakeService {

	private static final String CAKE_NOT_FOUND = "Cake not found for this id:: %s";
	private final CakeRepository cakeRepository;

	@Autowired
	public CakeService(CakeRepository cakeRepository) {
		this.cakeRepository = cakeRepository;
	}

	public CakesListResponse getAllCakes() {
		List<CakeEntity> dataObJ = cakeRepository.findAll();

		if (dataObJ.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data found!");
		}
		return new CakesListResponse(buildCakesList(dataObJ));
	}

	public Cake createCake(Cake cake) {
		CakeEntity cakeEntity = CakeUtils.buildCakeEntity(cake);
		return CakeUtils.buildCake(cakeRepository.save(cakeEntity));
	}

	public Cake getCakeById(long cakeId) throws ResourceNotFoundException {
		return CakeUtils.buildCake(cakeRepository.findById(cakeId)
			.orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(cakeId))));
	}

	public Cake updateCakeDetails(long cakeId,
		Cake cakeDetails) throws ResourceNotFoundException {
		CakeEntity cake = cakeRepository.findById(cakeId)
			.orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(cakeId)));

		cake.setCakeName(cakeDetails.getCakeName());
		cake.setCakeType(cakeDetails.getCakeType());
		cake.setPrice(cakeDetails.getPrice());
		return CakeUtils.buildCake(cakeRepository.save(cake));
	}

	public void deleteCake(long cakeId) throws ResourceNotFoundException {
		cakeRepository.findById(cakeId)
			.orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(cakeId)));
		cakeRepository.deleteById(cakeId);
	}

	private String getErrorMessage(long cakeId) {
		return String.format(CAKE_NOT_FOUND, cakeId);
	}
}
