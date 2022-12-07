package sit.oasip.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.oasip.entities.Event;
import sit.oasip.entities.Eventcategory;

import java.util.List;
import java.util.Optional;

public interface EventcategoryRepository extends JpaRepository<Eventcategory,Integer> {
    @Query("select ec from Eventcategory ec join EventCategoryOwner eco on ec.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId")
    List<Eventcategory> findEventCateByLecturer(Integer lecturerId);

    @Query("select ec from Eventcategory ec join EventCategoryOwner eco on ec.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId and ec.eventCategoryID = :eventCategoryID")
    Optional<Eventcategory> findEventCateByLecturerAndCateID(Integer lecturerId, Integer eventCategoryID);

    Eventcategory findByEventCategoryID(Integer eventCategoryId);
}
