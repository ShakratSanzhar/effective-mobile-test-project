package com.example.effectivemobiletestproject.web.dto.user;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserFilter {

    LocalDate birthday;
    String phone;
    String fullName;
    String email;
}
