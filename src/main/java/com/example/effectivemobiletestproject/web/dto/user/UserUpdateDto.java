package com.example.effectivemobiletestproject.web.dto.user;

import com.example.effectivemobiletestproject.web.dto.validation.UserInfo;
import com.example.effectivemobiletestproject.web.dto.validation.group.OnUpdate;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

@Data
@UserInfo(groups = {OnUpdate.class})
public class UserUpdateDto {

    @Hidden
    private Long id;
    private String email;
    private String phone;
}
