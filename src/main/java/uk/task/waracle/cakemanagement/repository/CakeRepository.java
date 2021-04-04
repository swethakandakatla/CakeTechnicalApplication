package uk.task.waracle.cakemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.task.waracle.cakemanagement.model.CakeEntity;

@Repository
public interface CakeRepository extends JpaRepository<CakeEntity,Long>{
	
	

}
