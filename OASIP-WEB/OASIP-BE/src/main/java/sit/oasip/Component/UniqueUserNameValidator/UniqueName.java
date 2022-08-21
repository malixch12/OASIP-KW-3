package sit.oasip.Component.UniqueUserNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUserNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD }) @Retention(RetentionPolicy.RUNTIME)
public @interface UniqueName {
    String message() default "User name is already exists";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}