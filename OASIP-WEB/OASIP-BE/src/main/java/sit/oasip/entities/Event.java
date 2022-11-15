package sit.oasip.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID", nullable = false)
    private Integer bookingId;

    @Column(name = "BookingName", nullable = false, length = 100)
    private String bookingName;

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

    @Column(name = "FileName")
    private String fileName;

    @Column(name = "EventCategoryID", nullable = false)
    private Integer eventCategoryID;       
}