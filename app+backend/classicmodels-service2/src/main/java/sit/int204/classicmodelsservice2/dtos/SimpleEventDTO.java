package sit.int204.classicmodelsservice2.dtos;

import java.sql.Date;
import java.time.Instant;
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

}
