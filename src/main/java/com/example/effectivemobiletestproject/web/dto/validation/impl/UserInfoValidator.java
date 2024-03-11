package com.example.effectivemobiletestproject.web.dto.validation.impl;

import com.example.effectivemobiletestproject.domain.entity.User;
import com.example.effectivemobiletestproject.repository.UserRepository;
import com.example.effectivemobiletestproject.web.dto.user.UserUpdateDto;
import com.example.effectivemobiletestproject.web.dto.validation.UserInfo;
import com.example.effectivemobiletestproject.web.security.JwtEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserUpdateDto> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UserUpdateDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDto.setId(((JwtEntity) authentication.getPrincipal()).getId());
        User user = userRepository.findById(userDto.getId()).get();
        String oldEmail = user.getEmail();
        String oldPhone = user.getPhone();
        String newEmail = userDto.getEmail();
        String newPhone = userDto.getPhone();
        if(newPhone.isEmpty() && newEmail.isEmpty()) {
            return false;
        }
        if((newPhone.isEmpty() && oldEmail.isEmpty()) || (newEmail.isEmpty() && oldPhone.isEmpty())) {
            return false;
        }
        return (newEmail.equals(oldEmail) || userRepository.findByEmail(newEmail).isEmpty()) && (newPhone.equals(oldPhone) || userRepository.findByPhone(newPhone).isEmpty());
    }
}
