package sit.int204.classicmodelsservice2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.classicmodelsservice2.entities.Eventcategory;

public interface EventcategoryRepository extends JpaRepository<Eventcategory,Integer> {
      
}
