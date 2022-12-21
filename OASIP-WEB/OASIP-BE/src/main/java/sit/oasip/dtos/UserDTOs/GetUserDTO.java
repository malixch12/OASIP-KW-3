package sit.oasip.dtos.UserDTOs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GetUserDTO {
    private Integer ID;
    private String Name;
    private String Email;
    private String Role;
    private Timestamp CreatedOn;
    private Timestamp UpdatedOn;
    private List<Categoryyyy> Owners;
}
