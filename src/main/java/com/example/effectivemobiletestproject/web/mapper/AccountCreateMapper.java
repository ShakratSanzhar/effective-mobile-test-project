package com.example.effectivemobiletestproject.web.mapper;

import com.example.effectivemobiletestproject.domain.entity.Account;
import com.example.effectivemobiletestproject.domain.entity.User;
import com.example.effectivemobiletestproject.repository.UserRepository;
import com.example.effectivemobiletestproject.web.dto.account.AccountCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountCreateMapper implements Mapper<AccountCreateDto, Account> {

    private final UserRepository userRepository;

    @Override
    public Account map(AccountCreateDto object) {
        Account account = new Account();
        account.setInitialDeposit(object.getInitialDeposit());
        account.setUser(getUser(object.getUserId()));
        return account;
    }

    private User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
