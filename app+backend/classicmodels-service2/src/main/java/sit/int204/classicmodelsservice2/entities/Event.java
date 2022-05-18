package sit.int204.classicmodelsservice2.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID", nullable = false)
    private Integer bookingId;

    // @NotBlank(message = "Winnerr")
    @Column(name = "BookingName", nullable = false, length = 100)
    private String bookingName;

    // @Email(message = "winner")
    // @Size(min = 1, max = 100)
    @Column(name = "BookingEmail", nullable = false, length = 100)
    private String bookingEmail;

    // @Size(min = 1, max = 100)
    @Column(name = "EventCategory", nullable = false, length = 100)
    private String eventCategory;

    // @NotBlank
    @Column(name = "EventStartTime", nullable = false)
    private Instant eventStartTime;

    @Column(name = "EventDuration", nullable = false)
    private Integer eventDuration;

    // @Size(min = 0, max = 500)
    @Column(name = "EventNotes", length = 500)
    private String eventNotes;

    @Column(name = "EventCategoryID", nullable = false)
    private Integer eventCategoryID;
        
}