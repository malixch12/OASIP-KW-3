package sit.int204.classicmodelsservice2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sit.int204.classicmodelsservice2.entities.Event;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer> {
    
 //   Page<Event> findByEventCategoryID(Integer eventCategoryID,Pageable pageable);
    List<Event> findByEventCategoryID(Integer eventCategoryID);


}
