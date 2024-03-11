package com.example.effectivemobiletestproject.web.dto.user;

import com.example.effectivemobiletestproject.web.dto.account.AccountCreateDto;
import com.example.effectivemobiletestproject.web.dto.validation.EmailInfo;
import com.example.effectivemobiletestproject.web.dto.validation.LoginInfo;
import com.example.effectivemobiletestproject.web.dto.validation.PhoneInfo;
import com.example.effectivemobiletestproject.web.dto.validation.group.OnCreate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserCreateDto {

    @NotEmpty(message = "Login shouldn't be empty", groups = {OnCreate.class})
    @LoginInfo(groups = {OnCreate.class})
    private String login;

    @NotEmpty(message = "Password shouldn't be empty", groups = {OnCreate.class})
    private String password;

    @NotEmpty(message = "Email shouldn't be empty", groups = {OnCreate.class})
    @EmailInfo(groups = {OnCreate.class})
    private String email;

    @NotEmpty(message = "Phone shouldn't be empty", groups = {OnCreate.class})
    @PhoneInfo(groups = {OnCreate.class})
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Birthday isn't valid",groups = {OnCreate.class})
    private LocalDate birthday;

    @NotEmpty(message = "Full name shouldn't be empty", groups = {OnCreate.class})
    private String fullName;

    private AccountCreateDto account;
}
