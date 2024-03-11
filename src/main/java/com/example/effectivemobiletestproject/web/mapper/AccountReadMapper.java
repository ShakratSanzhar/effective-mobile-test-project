package com.example.effectivemobiletestproject.web.mapper;

import com.example.effectivemobiletestproject.domain.entity.Account;
import com.example.effectivemobiletestproject.web.dto.account.AccountReadDto;
import org.springframework.stereotype.Component;

@Component
public class AccountReadMapper implements Mapper<Account, AccountReadDto> {

    @Override
    public AccountReadDto map(Account object) {
        AccountReadDto accountReadDto = new AccountReadDto();
        accountReadDto.setId(object.getId());
        accountReadDto.setBalance(object.getBalance());
        accountReadDto.setInitialDeposit(object.getInitialDeposit());
        return accountReadDto;
    }
}
