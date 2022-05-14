package sit.int204.classicmodelsservice2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sit.int204.classicmodelsservice2.entities.Event;

public interface EventRepository extends JpaRepository<Event,Integer> {
    
    Page<Event> findByEventCategoryID(Integer eventCategoryID,Pageable pageable);
   

}
