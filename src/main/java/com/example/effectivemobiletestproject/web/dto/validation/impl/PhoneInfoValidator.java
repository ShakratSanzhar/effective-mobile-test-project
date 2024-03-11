package com.example.effectivemobiletestproject.web.dto.validation.impl;

import com.example.effectivemobiletestproject.repository.UserRepository;
import com.example.effectivemobiletestproject.web.dto.validation.PhoneInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneInfoValidator implements ConstraintValidator<PhoneInfo,String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByPhone(value).isEmpty();
    }
}
