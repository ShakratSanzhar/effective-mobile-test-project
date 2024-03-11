package com.example.effectivemobiletestproject.web.dto.account;

import com.example.effectivemobiletestproject.web.dto.validation.group.OnCreate;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccountCreateDto {

    @NotNull(message = "Initial Deposit must be not null", groups = {OnCreate.class})
    @Positive(message = "Initial Deposit must be greater than zero", groups = {OnCreate.class})
    private Double initialDeposit;

    @Hidden
    private Long userId;
}
