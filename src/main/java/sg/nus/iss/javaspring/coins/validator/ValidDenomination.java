package sg.nus.iss.javaspring.coins.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { DenominationsValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidDenomination {
    String message() default "Denominations list contains invalid coin values";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
