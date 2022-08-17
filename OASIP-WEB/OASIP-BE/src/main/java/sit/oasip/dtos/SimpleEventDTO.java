package sit.oasip.dtos;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;

import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class SimpleEventDTO {
    private Integer BookingId;
    private String BookingName;
    private String BookingEmail;
    private String EventCategory;

    @Future(message = "Start Time must be future")
    private Instant EventStartTime;
    private Integer EventDuration;

    @Length(min = 0, max = 500, message = "size must be between 0 and 500")
    private String EventNotes;
    private Integer EventCategoryID;

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
        return formatter.format(EventStartTime);
    }

    public Instant getEndTime() {
        return EventStartTime.plusSeconds(EventDuration * 60);
    }


}
