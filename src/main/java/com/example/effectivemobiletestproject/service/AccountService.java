package com.example.effectivemobiletestproject.service;

import com.example.effectivemobiletestproject.domain.entity.Account;
import com.example.effectivemobiletestproject.domain.exception.ResourceNotFoundException;
import com.example.effectivemobiletestproject.repository.AccountRepository;
import com.example.effectivemobiletestproject.web.dto.account.AccountCreateDto;
import com.example.effectivemobiletestproject.web.dto.account.AccountReadDto;
import com.example.effectivemobiletestproject.web.dto.account.AccountUpdateDto;
import com.example.effectivemobiletestproject.web.mapper.AccountCreateMapper;
import com.example.effectivemobiletestproject.web.mapper.AccountReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountReadMapper accountReadMapper;
    private final AccountCreateMapper accountCreateMapper;

    public List<AccountReadDto> findAll() {
        log.info("Finding all accounts of all users");
       return accountRepository.findAll().stream().map(accountReadMapper::map).collect(Collectors.toList());
    }

    public Account getAccountById(Long id) {
        log.info("Getting account by id:{}",id);
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found."));
    }

    @Transactional
    public AccountReadDto update(AccountUpdateDto accountUpdateDto) {
        log.info("Updating account with id:{}",accountUpdateDto.getId());
        Account existingAccount = getAccountById(accountUpdateDto.getId());
        existingAccount.setBalance(accountUpdateDto.getBalance());
        Account updatedAccount = accountRepository.saveAndFlush(existingAccount);
        log.info("Account updated");
        return accountReadMapper.map(updatedAccount);
    }

    @Transactional
    public AccountReadDto create(AccountCreateDto accountCreateDto) {
        log.info("Creating new account");
        Account account = accountCreateMapper.map(accountCreateDto);
        account.setBalance(accountCreateDto.getInitialDeposit());
        Account newAccount = accountRepository.save(account);
        log.info("New account created");
        return accountReadMapper.map(newAccount);
    }
}
