package sit.int204.classicmodelsservice2.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

import javax.swing.text.DateFormatter;

@Getter
@Setter
public class SimpleEventDTO {
    private Integer BookingId;
    private String BookingName;
    private String BookingEmail;
    private String EventCategory;
    private LocalDateTime EventStartTime;
    private Integer EventDuration;
    private String EventNotes;
    private Integer EventCategoryID;
    public String getEventDate(){
        return EventStartTime.format(DateTimeFormatter.ofPattern("dd MMM YYYY"));
    }
    public String getEventTime(){
        return EventStartTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
