package sit.oasip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.oasip.entities.Event;
import sit.oasip.entities.EventCategoryOwner;
import sit.oasip.entities.Eventcategory;
import sit.oasip.entities.User;

import java.util.List;
import java.util.Optional;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner,Integer> {
   EventCategoryOwner findById(int ownerId);
   List<EventCategoryOwner> findByEventCategoryID(Eventcategory cateId);
   List<EventCategoryOwner> findByUserID(Optional<User> userId);

   @Query("select ec.eventCategoryID from EventCategoryOwner ec where ec.userID.UserId = :userID")
   List<Eventcategory> findCategoryName(int userID);


   EventCategoryOwner findByEventCategoryIDAndAndUserID(Eventcategory cateId, User userId);

}
