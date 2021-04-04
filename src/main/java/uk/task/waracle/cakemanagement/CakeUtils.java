package uk.task.waracle.cakemanagement;

import uk.task.waracle.cakemanagement.model.CakeEntity;
import uk.task.waracle.cakemanagement.rest.datatype.Cake;

import java.util.ArrayList;
import java.util.List;

public class CakeUtils {

	private CakeUtils() {

	}

	public static Cake buildCake(CakeEntity cakeEntity) {
		Cake cake = new Cake();
		cake.setCakeName(cakeEntity.getCakeName());
		cake.setCakeType(cakeEntity.getCakeType());
		cake.setId(cakeEntity.getId());
		cake.setPrice(cakeEntity.getPrice());
		return cake;
	}

	public static CakeEntity buildCakeEntity(Cake cake) {
		CakeEntity cakeEntity = new CakeEntity();
		cakeEntity.setCakeName(cake.getCakeName());
		cakeEntity.setCakeType(cake.getCakeType());
		cakeEntity.setId(cake.getId());
		cakeEntity.setPrice(cake.getPrice());
		return cakeEntity;
	}

	public static List<Cake> buildCakesList(List<CakeEntity> cakeEntityList) {
		List<Cake> cakesList = new ArrayList<>();
		if(cakeEntityList!=null && !cakeEntityList.isEmpty()) {
			 for(CakeEntity cakeEntity : cakeEntityList) {
			 	cakesList.add(buildCake(cakeEntity));
			 }
		}
		return cakesList;
	}
}
