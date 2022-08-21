package sit.oasip.Component.UniqueUserNameValidator;

import org.springframework.beans.factory.annotation.Autowired;
import sit.oasip.repositories.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueName, String> {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if(userRepository.findByName(name) != null)
        {
            return userRepository.findByName(name).getName() == name;
        }
        return true;
    }
}
