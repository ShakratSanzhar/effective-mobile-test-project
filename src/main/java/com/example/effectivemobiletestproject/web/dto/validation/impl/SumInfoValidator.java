package com.example.effectivemobiletestproject.web.dto.validation.impl;

import com.example.effectivemobiletestproject.repository.UserRepository;
import com.example.effectivemobiletestproject.web.dto.validation.SumInfo;
import com.example.effectivemobiletestproject.web.security.JwtEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SumInfoValidator implements ConstraintValidator<SumInfo,Double> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(Double sum, ConstraintValidatorContext constraintValidatorContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long authenticatedUserId = ((JwtEntity) authentication.getPrincipal()).getId();
        Double balance = userRepository.findById(authenticatedUserId).get().getAccount().getBalance();
        return balance >= sum;
    }
}
