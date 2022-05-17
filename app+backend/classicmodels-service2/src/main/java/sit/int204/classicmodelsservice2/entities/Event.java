package sit.int204.classicmodelsservice2.entities;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID", nullable = false)
    private Integer bookingId;

    @Column(name = "BookingName", nullable = false, length = 100)
    private String bookingName;

    @Email
    @Column(name = "BookingEmail", nullable = false, length = 100)
    private String bookingEmail;

    @Column(name = "EventCategory", nullable = false, length = 100)
    private String eventCategory;

    @Column(name = "EventStartTime", nullable = false)
    private Instant eventStartTime;

    @Column(name = "EventDuration", nullable = false)
    private Integer eventDuration;

    @Column(name = "EventNotes", length = 500)
    private String eventNotes;

    @Column(name = "EventCategoryID", nullable = false)
    private Integer eventCategoryID;

    
}