package sit.oasip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.oasip.entities.Eventcategory;

public interface EventcategoryRepository extends JpaRepository<Eventcategory,Integer> {
      
}
