package sit.oasip.dtos.UserDTOs;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import sit.oasip.Component.UniqueEmailValidator.UniqueEmail;
import sit.oasip.Component.UniqueUserNameValidator.UniqueName;

import javax.validation.constraints.Email;


@Getter
@Setter
public class EditUserDTO {
    @UniqueName
    @Length(max = 100, message="size must be between 1 and 100")
    private String Name;

    @UniqueEmail
    @Email(message="please check email format")
    @Length(max = 50, message="size must be between 1 and 50")
    private String Email;

    private String Role;
}