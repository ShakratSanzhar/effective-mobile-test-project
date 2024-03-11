package com.example.effectivemobiletestproject.web.dto.validation;

import com.example.effectivemobiletestproject.web.dto.validation.impl.LoginInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LoginInfoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginInfo {

    String message() default "This login is already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
