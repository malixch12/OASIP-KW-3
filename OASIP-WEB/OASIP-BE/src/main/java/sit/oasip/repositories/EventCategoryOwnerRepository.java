package sit.oasip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.oasip.entities.Event;
import sit.oasip.entities.EventCategoryOwner;
import sit.oasip.entities.User;

import java.util.List;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner,Integer> {
   List<EventCategoryOwner> findByUserID(User userId);

   @Query("select ec from EventCategoryOwner ec where ec.userID.UserId = :userID")
   List<EventCategoryOwner> findCategoryName(int userID);


   Integer countAllByEventCategoryID(Optional<Eventcategory> eventcategory);

   EventCategoryOwner findByEventCategoryIDAndAndUserID(Eventcategory cateId, User userId);

}
