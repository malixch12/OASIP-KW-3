package sit.int204.classicmodelsservice2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sit.int204.classicmodelsservice2.entities.Event;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
        Page<Event> findByEventCategoryID(Integer eventCategoryID, Pageable pageable);

        Page<Event> findByEventCategoryIDAndEventStartTimeLessThan(Integer eventCategoryID, Instant dateNow,
                        Pageable pageable);

        Page<Event> findByEventCategoryIDAndEventStartTimeGreaterThan(Integer eventCategoryID, Instant dateNow,
                        Pageable pageable);

        Page<Event> findByEventCategoryIDAndEventStartTimeEquals(Integer eventCategoryID,Instant date, Pageable pageable);

        List<Event> findByEventStartTimeLessThan(Instant dateNow);

        List<Event> findByEventStartTimeGreaterThan(Instant dateNow);

        List<Event> findByEventStartTimeEquals(Instant date);
}
