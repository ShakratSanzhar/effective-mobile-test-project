package com.example.effectivemobiletestproject.web.controller;

import com.example.effectivemobiletestproject.service.UserService;
import com.example.effectivemobiletestproject.utils.PageableUtils;
import com.example.effectivemobiletestproject.web.dto.user.UserCreateDto;
import com.example.effectivemobiletestproject.web.dto.user.UserFilter;
import com.example.effectivemobiletestproject.web.dto.user.UserReadDto;
import com.example.effectivemobiletestproject.web.dto.user.UserUpdateDto;
import com.example.effectivemobiletestproject.web.dto.validation.group.OnCreate;
import com.example.effectivemobiletestproject.web.dto.validation.group.OnUpdate;
import com.example.effectivemobiletestproject.web.security.JwtEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "User Controller",
        description = "User API"
)
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/page/{page}/size/{size}")
    @Operation(summary = "Get all users by filter", description = "Get all users sorted by filter page by page. Enter the birthday date in the format yyyy-mm-dd")
    public List<UserReadDto> findAll(@PathVariable Integer page, @PathVariable Integer size,
                                     @RequestParam(required = false) @Parameter(description = "Birthday date after. Format: yyyy-mm-dd") LocalDate birthday,
                                     @RequestParam(required = false) @Parameter(description = "Complete match on the phone") String phone,
                                     @RequestParam(required = false) @Parameter(description = "Full name starts with") String fullName,
                                     @RequestParam(required = false) @Parameter(description = "Complete match on the email") String email) {
        log.info("Request to get all users by birthday:{},phone:{},full name:{},email:{}",birthday,phone,fullName,email);
        UserFilter userFilter = UserFilter.builder()
                .birthday(birthday)
                .email(email)
                .phone(phone)
                .fullName(fullName)
                .build();
        Pageable pageable = PageableUtils.getSortedPageable(page, size, Sort.Direction.ASC, "login");
        List<UserReadDto> users = userService.findAll(userFilter,pageable).getContent();
        log.info("Received users by filter");
        return users;
    }

    @PatchMapping
    @Operation(summary = "Update authenticated user", description = "Update or delete email and phone of authenticated user")
    public UserReadDto update(
            @Validated(OnUpdate.class)
            @RequestBody UserUpdateDto dto
    ) {
        log.info("Request to update authenticated user by email:{},phone:{}",dto.getEmail(),dto.getPhone());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        dto.setId(((JwtEntity) authentication.getPrincipal()).getId());
        UserReadDto updatedUser = userService.update(dto);
        log.info("Authenticated user successfully updated");
        return updatedUser;
    }

    @PostMapping
    @Operation(summary = "Add new user", description = "Service API with open access")
    public UserReadDto create(
            @Validated(OnCreate.class)
            @RequestBody UserCreateDto dto
    ) {
        log.info("Request to add new user:{}",dto);
        UserReadDto createdUser =   userService.create(dto);
        log.info("New user with id:{} created",createdUser.getId());
        return createdUser;
    }
}
