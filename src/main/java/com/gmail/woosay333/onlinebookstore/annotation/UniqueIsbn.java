package com.gmail.woosay333.onlinebookstore.annotation;

import com.gmail.woosay333.onlinebookstore.validation.UniqueIsbnValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueIsbnValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UniqueIsbn {
    String message() default "ISBN is not unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
