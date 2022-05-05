package sit.int204.classicmodelsservice2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "event")
public class Event {
    @Id  
    // @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "BookingID", nullable = false)
    private Integer BookingId;

    @Column(name = "BookingName", nullable = false, length = 100)
    private String bookingName;

    @Column(name = "BookingEmail", nullable = false, length = 100)
    private String bookingEmail;

    @Column(name = "EventCategory", nullable = false, length = 100)
    private String eventCategory;

    @Column(name = "EventStartTime", nullable = false)
    private LocalDateTime eventStartTime;

    @Column(name = "EventDuration", nullable = false)
    private Integer eventDuration;

    @Column(name = "EventNotes", length = 500)
    private String eventNotes;

    @Column(name = "EventCategoryID", nullable = false)
    private Integer eventCategoryID;
    
}