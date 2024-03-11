package com.example.effectivemobiletestproject.web.dto.account;

import lombok.Data;

@Data
public class AccountReadDto {

    private Long id;
    private Double balance;
    private Double initialDeposit;
}
