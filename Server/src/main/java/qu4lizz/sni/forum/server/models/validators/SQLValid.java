package qu4lizz.sni.forum.server.models.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SQLValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLValid {
    String message() default "Invalid SQL query";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}