package sit.oasip.dtos.UserDTOs;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MatchUserDTO   {


    @Email(message="please check email format")
    @NotBlank(message = "please fill email")
    @Length(min = 1, max = 50, message="size must be between 1 and 50")
    String Email;

    @NotBlank(message = "please fill password")
    @Length(min = 8, max = 14, message="size must be between 8 and 14")
    String Password;
}
