package sit.oasip.dtos.EventDTOs;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;

import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class GetEventDTO {
    private Integer BookingId;
    private String BookingName;
    private String BookingEmail;
    private String EventCategory;
    private Instant EventStartTime;
    private Integer EventDuration;
    private String EventNotes;
    private String FileName;
    private Integer EventCategoryID;

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Asia/Bangkok"));
        return formatter.format(EventStartTime);
    }

    public Instant getEndTime() {
        return EventStartTime.plusSeconds(EventDuration * 60);
    }


}
