package uk.task.waracle.cakemanagement.rest.datatype;

import java.util.List;

public class CakesListResponse {

	private final List<Cake> cake;

	public CakesListResponse(List<Cake> cake) {
		this.cake = cake;
	}

	public List<Cake> getCake() {
		return cake;
	}
}
