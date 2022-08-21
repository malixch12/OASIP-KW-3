package sit.oasip.dtos.UserDTO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import UniqueEmail;
import UniqueName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class AddUserDTO {
    @UniqueName
    @NotBlank(message = "please fill user name")
    @Length(min = 1, max = 100, message="size must be between 1 and 100")
    private String Name;


    @Email
    @UniqueEmail
    @NotBlank(message = "please fill user name")
    @Length(min = 1, max = 50, message="size must be between 1 and 50")
    private String Email;
    private String Role = "Student";


}