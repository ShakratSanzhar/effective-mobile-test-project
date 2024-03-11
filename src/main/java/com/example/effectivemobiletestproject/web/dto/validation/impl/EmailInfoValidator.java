package com.example.effectivemobiletestproject.web.dto.validation.impl;

import com.example.effectivemobiletestproject.repository.UserRepository;
import com.example.effectivemobiletestproject.web.dto.validation.EmailInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailInfoValidator implements ConstraintValidator<EmailInfo,String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByEmail(value).isEmpty();
    }
}
