package sit.oasip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.oasip.entities.Event;
import sit.oasip.entities.EventCategoryOwner;
import sit.oasip.entities.User;

import java.util.List;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner,Integer> {
   List<EventCategoryOwner> findByUserID(User userId);

}
