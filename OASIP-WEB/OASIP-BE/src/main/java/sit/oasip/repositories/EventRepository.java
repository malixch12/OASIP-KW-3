package sit.oasip.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import sit.oasip.dtos.EventDTOs.AddEventDTO;
import sit.oasip.entities.Event;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
        @Query("select e.fileName from Event e")
        List<String> findFileName();

        List<Event> findAll(Sort sort);
        List<Event> findByBookingEmail(String email,Sort sort);
        List<Event> findByEventCategoryID(Integer eventCategoryID,Sort sort);

        Event findByBookingId(Integer eventId);
        Event findByFileName(String filename);
        //admin
        List<Event> findByEventCategoryIDAndEventStartTimeLessThan(Integer eventCategoryID, Instant dateNow,Sort sort);
        List<Event> findByEventCategoryIDAndEventStartTimeGreaterThan(Integer eventCategoryID, Instant dateNow,Sort sort);
        List<Event> findByEventCategoryIDAndEventStartTimeEquals(Integer eventCategoryID,Instant date);

        List<Event> findByEventStartTimeLessThan(Instant dateNow,Sort sort);
        List<Event> findByEventStartTimeGreaterThan(Instant dateNow,Sort sort);
        List<Event> findByEventStartTimeEquals(Instant date,Sort sort);

        //lecturer
        @Query("select e from Event e join EventCategoryOwner eco on e.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId and e.bookingId = :eventId")
        Optional<Event> findEventByLecturerAndEventID(Integer lecturerId,Integer eventId);

        @Query("select e from Event e join EventCategoryOwner eco on e.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId")
        List<Event> findAllEventByLecturerCategory(Integer lecturerId,Sort sort);
        @Query("select e from Event e join EventCategoryOwner eco on e.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId and e.eventStartTime = :date")
        List<Event> findAllEventByLecturerStartTimeEquals(Integer lecturerId,Sort sort,Instant date);
        @Query("select e from Event e join EventCategoryOwner eco on e.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId and e.eventStartTime > :date")
        List<Event> findAllEventByLecturerStartTimeGreaterThan(Integer lecturerId,Sort sort,Instant date);
        @Query("select e from Event e join EventCategoryOwner eco on e.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId and e.eventStartTime < :date")
        List<Event> findAllEventByLecturerStartTimeLessThan(Integer lecturerId,Sort sort,Instant date);

        @Query("select e from Event e join EventCategoryOwner eco on e.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId and e.eventCategoryID = :eventCategoryID")
        List<Event> findAllEventByLecturerCategoryId(Integer lecturerId,Integer eventCategoryID,Sort sort);
        @Query("select e from Event e join EventCategoryOwner eco on e.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId and e.eventCategoryID = :eventCategoryID and e.eventStartTime = :date")
        List<Event> findAllEventByLecturerCategoryIdAndEventStartTimeEquals(Integer lecturerId,Integer eventCategoryID,Instant date);
        @Query("select e from Event e join EventCategoryOwner eco on e.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId and e.eventCategoryID = :eventCategoryID and e.eventStartTime > :date")
        List<Event> findAllEventByLecturerCategoryIdAndEventStartTimeGreaterThan(Integer lecturerId,Integer eventCategoryID,Instant date,Sort sort);
        @Query("select e from Event e join EventCategoryOwner eco on e.eventCategoryID = eco.eventCategoryID.eventCategoryID where eco.userID.id = :lecturerId and e.eventCategoryID = :eventCategoryID and e.eventStartTime < :date")
        List<Event> findAllEventByLecturerCategoryIdAndEventStartTimeLessThan(Integer lecturerId,Integer eventCategoryID,Instant date,Sort sort);

        //student
        @Query(value = "select e from Event e  where e.bookingEmail = :email")
        List<Event> findAllEventByStudent(String email,Sort sort);
        @Query(value = "select e from Event e  where e.bookingEmail = :email and e.eventStartTime = :date")
        List<Event> findAllEventByStudentStartTimeEquals(String email,Sort sort ,Instant date);
        @Query(value = "select e from Event e  where e.bookingEmail = :email and e.eventStartTime > :date")
        List<Event> findAllEventByStudentStartTimeGreaterThan(String email,Sort sort ,Instant date);
        @Query(value = "select e from Event e  where e.bookingEmail = :email and e.eventStartTime < :date")
        List<Event> findAllEventByStudentStartTimeLessThan(String email,Sort sort ,Instant date);

        @Query("select e from Event e  where e.bookingEmail = :email and e.eventCategoryID = :eventCategoryID")
        List<Event> findAllEventByStudentCategoryId(String email,Integer eventCategoryID,Sort sort);
        @Query("select e from Event e  where e.bookingEmail = :email and e.eventCategoryID = :eventCategoryID and e.eventStartTime = :date")
        List<Event> findAllEventByStudentCategoryIdAndEventStartTimeEquals(String email,Integer eventCategoryID,Sort sort,Instant date);
        @Query("select e from Event e  where e.bookingEmail = :email and e.eventCategoryID = :eventCategoryID and e.eventStartTime > :date")
        List<Event> findAllEventByStudentCategoryIdAndEventStartTimeGreaterThan(String email,Integer eventCategoryID,Sort sort,Instant date);
        @Query("select e from Event e  where e.bookingEmail = :email and e.eventCategoryID = :eventCategoryID and e.eventStartTime < :date")
        List<Event> findAllEventByStudentCategoryIdAndEventStartTimeLessThan(String email,Integer eventCategoryID,Sort sort,Instant date);
}
