package com.example.effectivemobiletestproject.web.controller;

import com.example.effectivemobiletestproject.service.TransferService;
import com.example.effectivemobiletestproject.web.dto.transfer.TransferRequest;
import com.example.effectivemobiletestproject.web.dto.validation.group.OnCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Transfer Controller",
        description = "Transfer API"
)
@Slf4j
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    @Operation(summary = "Transfer money", description = "Transfer money to another user")
    public boolean transferMoney(@Validated(OnCreate.class) @RequestBody TransferRequest transferRequest) {
        log.info("Request to transfer money to user with login:{}", transferRequest.getLogin());
        boolean success = transferService.transferMoney(transferRequest);
        if (success) {
            log.info("Money transfer done successfully");
        } else {
            log.warn("Money transfer failed");
        }
        return success;
    }
}
