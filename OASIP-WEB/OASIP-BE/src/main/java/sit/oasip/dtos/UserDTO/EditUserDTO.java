package sit.oasip.dtos.UserDTO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EditUserDTO {

    @Length(max = 100, message="size must be between 1 and 100")
    private String UserName;

    @Email(message = "format is wrong")
    @Length(max = 50, message="size must be between 1 and 50")
    private String Email;

    private String Role;
}