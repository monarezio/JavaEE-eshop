package cz.kodytek.common.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MatchesValidator.class)
@Documented
public @interface Matches {

    String message() default "{constraints.matches";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String targetFieldName();

    String name();

}
