package sit.oasip.dtos.EventDTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class GetDateTimeEvent {
    private Integer BookingId;
    private String EventCategory;
    private Instant EventStartTime;
    private Integer EventDuration;

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Asia/Bangkok"));
        return formatter.format(EventStartTime);
    }

    public Instant getEndTime() {
        return EventStartTime.plusSeconds(EventDuration * 60);
    }
}