package com.example.effectivemobiletestproject.service;

import com.example.effectivemobiletestproject.domain.entity.Account;
import com.example.effectivemobiletestproject.domain.entity.User;
import com.example.effectivemobiletestproject.web.dto.account.AccountReadDto;
import com.example.effectivemobiletestproject.web.dto.account.AccountUpdateDto;
import com.example.effectivemobiletestproject.web.dto.transfer.TransferRequest;
import com.example.effectivemobiletestproject.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransferService {

    private static final Double DEPOSIT_RATIO = 1.05;
    private static final Double MAX_DEPOSIT_RATIO = 2.07;

    private final UserService userService;
    private final AccountService accountService;

    public boolean transferMoney(TransferRequest transferRequest) {
        log.info("Transferring money to user with login:{}",transferRequest.getLogin());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User senderUser = userService.getUserById(((JwtEntity) authentication.getPrincipal()).getId());
            User receiverUser = userService.getByUsername(transferRequest.getLogin());
            Account senderAccount = senderUser.getAccount();
            Account receiverAccount = receiverUser.getAccount();
            if(senderAccount.getBalance()<transferRequest.getSum()) {
                throw new IllegalStateException();
            }
            AccountUpdateDto updatedSenderAccount = AccountUpdateDto.builder()
                    .id(senderAccount.getId())
                    .balance(senderAccount.getBalance() - transferRequest.getSum())
                    .build();
            accountService.update(updatedSenderAccount);
            AccountUpdateDto updatedReceiverAccount = AccountUpdateDto.builder()
                    .id(receiverAccount.getId())
                    .balance(receiverAccount.getBalance() + transferRequest.getSum())
                    .build();
            accountService.update(updatedReceiverAccount);
            log.info("Transferring money complete successfully");
            return true;
        } catch (Exception exception) {
            log.warn("Transferring money failed");
            return false;
        }
    }

    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void updateBalanceForAllUsers() {
        log.info("Accrual of the deposit starts");
        List<AccountReadDto> accounts = accountService.findAll();
        for (AccountReadDto account : accounts) {
            double limit = account.getInitialDeposit() * MAX_DEPOSIT_RATIO;
            double currentBalance = account.getBalance();
            double newBalance = currentBalance * DEPOSIT_RATIO;
            if (newBalance > limit) {
                continue;
            }
            accountService.update(AccountUpdateDto.builder().id(account.getId()).balance(newBalance).build());
        }
        log.info("Accrual of the deposit ends");
    }
}
