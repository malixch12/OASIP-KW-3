package sit.int204.classicmodelsservice2.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

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

    @NotBlank(message = "Booking name must not be blank")
    @Length(min=1, max = 100, message="size must be between 1 and 100")
    @Column(name = "BookingName", nullable = false, length = 100)
    private String bookingName;

    @Email
    @NotBlank(message = "Email must not be blank")
    @Length(min = 1, max = 100, message="size must be between 1 and 100")
    @Column(name = "BookingEmail", nullable = false, length = 100)
    private String bookingEmail;

    @Length(min = 1, max = 100, message="size must be between 1 and 100")
    @Column(name = "EventCategory", nullable = false, length = 100)
    private String eventCategory;
    @NotNull(message = "Time is not null")
    @Column(name = "EventStartTime", nullable = false)
    private Instant eventStartTime;

    @Column(name = "EventDuration", nullable = false)
    private Integer eventDuration;

    @Length(min = 0, max = 500, message = "size must be between 0 and 500")
    @Column(name = "EventNotes", length = 500)
    private String eventNotes;

    @NotNull(message = "Category ID is not null")
    @Column(name = "EventCategoryID", nullable = false)
    private Integer eventCategoryID;       
}