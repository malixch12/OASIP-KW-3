package sit.int204.classicmodelsservice2.dtos;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import sit.int204.classicmodelsservice2.entities.Event;

import javax.swing.text.DateFormatter;

import org.springframework.data.domain.Page;

@Getter
@Setter
public class SimpleEventDTO {
    private Integer BookingId;
    private String BookingName;
    private String BookingEmail;
    private String EventCategory;
    private Instant EventStartTime;
    private Integer EventDuration;
    private String EventNotes;
    private Integer EventCategoryID;

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
        return formatter.format(EventStartTime);
    }
}
