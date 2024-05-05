package org.shanthan.customerdataservice.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateOfBirthValidator.class)
public @interface DateOfBirth {

    String message() default "Invalid date format. Must be -> MM/dd/yyyy!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
