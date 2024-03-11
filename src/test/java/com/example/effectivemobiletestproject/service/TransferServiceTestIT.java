package com.example.effectivemobiletestproject.service;

import com.example.effectivemobiletestproject.IntegrationTestBase;
import com.example.effectivemobiletestproject.web.dto.transfer.TransferRequest;
import com.example.effectivemobiletestproject.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class TransferServiceTestIT extends IntegrationTestBase {

    private static final String SENDER_LOGIN = "Sanzhar";
    private static final String SENDER_PASSWORD = "123";
    private static final Long SENDER_ID = 1L;
    private static final String RECEIVER_LOGIN = "Bekzat";
    private static final double SUCCESS_SUM = 20.0;
    private static final double FAILED_SUM = 1000.0;
    private static final String NOT_VALID_LOGIN = "stupid";

    private final TransferService transferService;
    private final UserService userService;

    @BeforeEach
    public void setup() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(new JwtEntity(SENDER_ID,SENDER_LOGIN,SENDER_PASSWORD), "");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @Order(1)
    void transferMoneySuccessfully() {
        double sendersOldBalance = userService.getByUsername(SENDER_LOGIN).getAccount().getBalance();
        double receiversOldBalance = userService.getByUsername(RECEIVER_LOGIN).getAccount().getBalance();
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSum(SUCCESS_SUM);
        transferRequest.setLogin(RECEIVER_LOGIN);

        boolean actualResult = transferService.transferMoney(transferRequest);
        double sendersNewBalance = userService.getByUsername(SENDER_LOGIN).getAccount().getBalance();
        double receiversNewBalance = userService.getByUsername(RECEIVER_LOGIN).getAccount().getBalance();

        Assertions.assertThat(actualResult).isTrue();
        Assertions.assertThat(sendersNewBalance).isEqualTo(sendersOldBalance - SUCCESS_SUM);
        Assertions.assertThat(receiversNewBalance).isEqualTo(receiversOldBalance + SUCCESS_SUM);
    }

    @Test
    @Order(2)
    void transferMoneyFailedWithNotValidLogin() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSum(SUCCESS_SUM);
        transferRequest.setLogin(NOT_VALID_LOGIN);

        boolean actualResult = transferService.transferMoney(transferRequest);

        Assertions.assertThat(actualResult).isFalse();
    }

    @Test
    @Order(3)
    void transferMoneyFailedWithNotValidSum() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSum(FAILED_SUM);
        transferRequest.setLogin(RECEIVER_LOGIN);

        boolean actualResult = transferService.transferMoney(transferRequest);

        Assertions.assertThat(actualResult).isFalse();
    }
}
