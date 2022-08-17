package sit.oasip.dtos.UserDTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserDTO {
    private Integer UserId;
    private String UserName;
    private String Email;
    private String Role;
    private Timestamp CreateOn;
    private Timestamp UpdateOn;

}
