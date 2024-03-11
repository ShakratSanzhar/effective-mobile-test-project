package com.example.effectivemobiletestproject.web.dto.account;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountUpdateDto {

    @Hidden
    private Long id;

    private Double balance;
}
