package sit.int204.classicmodelsservice2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import sit.int204.classicmodelsservice2.entities.Event;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
        List<Event> findAll(Sort sort);
        List<Event> findByEventCategoryID(Integer eventCategoryID,Sort sort);

        List<Event> findByEventCategoryIDAndEventStartTimeLessThan(Integer eventCategoryID, Instant dateNow,Sort sort);

        List<Event> findByEventCategoryIDAndEventStartTimeGreaterThan(Integer eventCategoryID, Instant dateNow);

        List<Event> findByEventCategoryIDAndEventStartTimeEquals(Integer eventCategoryID,Instant date);

        List<Event> findByEventStartTimeLessThan(Instant dateNow,Sort sort);

        List<Event> findByEventStartTimeGreaterThan(Instant dateNow,Sort sort);

        List<Event> findByEventStartTimeEquals(Instant date,Sort sort);

        Optional<Event> saveAndFlush(Optional<Event> event);
}
