package com.example.effectivemobiletestproject.web.dto.user;

import com.example.effectivemobiletestproject.web.dto.account.AccountReadDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserReadDto {

    private Long id;
    private String login;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String fullName;
    private AccountReadDto account;
}
