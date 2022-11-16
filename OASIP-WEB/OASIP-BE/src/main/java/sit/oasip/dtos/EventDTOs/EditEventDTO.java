package sit.oasip.dtos.EventDTOs;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.validation.constraints.Future;
import java.time.Instant;

@Getter
@Setter
public class EditEventDTO {
    @Future(message = "Start Time must be future")
    private Instant EventStartTime;

    @Length(min = 0, max = 500, message = "size must be between 0 and 500")
    private String EventNotes;

    private MultipartFile File;
}
