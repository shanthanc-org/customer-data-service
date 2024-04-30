package org.shanthan.customerdataservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StateValidator.class)
public @interface ValidState {

    String message() default "Value of 'state' in address is invalid! ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
