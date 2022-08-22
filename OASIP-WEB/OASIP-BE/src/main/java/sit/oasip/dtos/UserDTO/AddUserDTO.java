package sit.oasip.dtos.UserDTO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import sit.oasip.Component.UniqueEmailValidator.UniqueEmail;
import sit.oasip.Component.UniqueUserNameValidator.UniqueName;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class AddUserDTO {
    @UniqueName
    @NotBlank(message = "please fill user name")
    @Length(min = 1, max = 100, message="size must be between 1 and 100")
    private String Name;



    @UniqueEmail

    @Email(message="please check email format")

    @NotBlank(message = "please fill email")
    @Length(min = 1, max = 50, message="size must be between 1 and 50")
    private String Email;
    private String Role = "Student";


}