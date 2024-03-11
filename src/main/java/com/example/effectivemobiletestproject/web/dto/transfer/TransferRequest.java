package com.example.effectivemobiletestproject.web.dto.transfer;

import com.example.effectivemobiletestproject.web.dto.validation.LoginExistInfo;
import com.example.effectivemobiletestproject.web.dto.validation.SumInfo;
import com.example.effectivemobiletestproject.web.dto.validation.group.OnCreate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferRequest {

    @NotNull(message = "Sum must be not null", groups = {OnCreate.class})
    @SumInfo(groups = {OnCreate.class})
    private Double sum;

    @NotNull(message = "Login must be not null", groups = {OnCreate.class})
    @LoginExistInfo(groups = {OnCreate.class})
    private String login;
}
