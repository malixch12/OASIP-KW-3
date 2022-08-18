package sit.oasip.dtos.UserDTO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddUserDTO {
    @NotBlank(message = "User name must not be blank !!")
    @Length(min = 1, max = 100, message="size must be between 1 and 100")
    private String UserName;

    @Email
    @NotBlank(message = "Email must not be blank !!")
    @Length(min = 1, max = 50, message="size must be between 1 and 50")
    private String Email;
    private String Role;
}
