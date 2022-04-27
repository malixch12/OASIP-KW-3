package sit.int204.classicmodelsservice2.dtos;

import java.sql.Date;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleEventDTO {
    private Integer BookingId;
    private String BookingName;
    private String BookingEmail;
    private String EventCategory;
    private Instant EventStartTime;
    private String EventNotes;
    private Integer EventCategoryID;

}
