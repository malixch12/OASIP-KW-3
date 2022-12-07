package sit.oasip.dtos.UserDTOs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
    private Map Owners;

//    public Integer getOwners() {
//        return Owners.toString();
//    }
}
