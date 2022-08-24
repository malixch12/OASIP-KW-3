package sit.oasip.dtos.UserDTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserDTO {
    private Integer ID;
    private String Name;
    private String Email;
    private String Password;
    private String Role;
    private Timestamp CreatedOn;
    private Timestamp UpdatedOn;

}
