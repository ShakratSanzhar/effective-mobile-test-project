package com.example.effectivemobiletestproject.web.dto.validation;

import com.example.effectivemobiletestproject.web.dto.validation.impl.SumInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SumInfoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SumInfo {

    String message() default "Not enough money in the account";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
