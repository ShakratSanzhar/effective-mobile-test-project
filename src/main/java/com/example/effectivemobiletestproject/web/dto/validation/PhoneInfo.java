package com.example.effectivemobiletestproject.web.dto.validation;

import com.example.effectivemobiletestproject.web.dto.validation.impl.PhoneInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneInfoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneInfo {

    String message() default "This phone is already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
