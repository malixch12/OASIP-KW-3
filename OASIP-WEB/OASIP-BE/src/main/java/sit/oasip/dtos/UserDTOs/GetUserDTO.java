package sit.oasip.dtos.UserDTOs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class GetUserDTO {
    private Integer ID;
    private String Name;
    private String Email;
    private String Password;
    private String Role;
    private Timestamp CreatedOn;
    private Timestamp UpdatedOn;

}
