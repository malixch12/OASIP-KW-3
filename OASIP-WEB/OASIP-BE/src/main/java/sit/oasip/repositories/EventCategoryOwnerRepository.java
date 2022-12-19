package sit.oasip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.oasip.entities.*;
import org.springframework.data.jpa.repository.Query;
import sit.oasip.entities.User;

import java.util.List;
import java.util.Optional;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner,Integer> {
   EventCategoryOwner findById(int ownerId);
   List<EventCategoryOwner> findByEventCategoryID(Eventcategory cateId);
   List<EventCategoryOwner> findByUserID(int userId);

   @Query("select ec from EventCategoryOwner ec where ec.userID.UserId = :userID")
   List<EventCategoryOwner> findCategoryName(int userID);

   Integer countAllByEventCategoryID(Optional<Eventcategory> eventcategory);

   EventCategoryOwner findByEventCategoryIDAndAndUserID(Eventcategory cateId, User userId);

}
