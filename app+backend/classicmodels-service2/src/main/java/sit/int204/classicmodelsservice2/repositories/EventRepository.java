package sit.int204.classicmodelsservice2.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.classicmodelsservice2.entities.Event;

public interface EventRepository extends JpaRepository<Event,Integer> {
    

    // List<Customer> findAllByCreditLimitBetween(BigDecimal lower,BigDecimal upper);
    List<Event> findByEventCategoryID(Integer eventCategoryID);
}
