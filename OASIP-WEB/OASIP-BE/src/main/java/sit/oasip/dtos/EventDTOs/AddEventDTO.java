package sit.oasip.dtos.EventDTOs;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter @Setter
public class AddEventDTO {

    private int BookingId;

    @NotBlank(message = "Booking name must not be blank")
    @Length(min=1, max = 100, message="size must be between 1 and 100")
    private String BookingName;

    @Email
    @NotBlank(message = "Email must not be blank")
    @Length(min = 1, max = 100, message="size must be between 1 and 100")
    private String BookingEmail;
    private String EventCategory;

    @Future(message = "Start Time must be future")
    @NotNull(message = "Time is not null")
    private Instant EventStartTime;
    private Integer EventDuration;

    @Length(min = 0, max = 500, message = "size must be between 0 and 500")
    private String EventNotes;

    @NotNull(message = "Category ID is not null")
    private Integer EventCategoryID;
}