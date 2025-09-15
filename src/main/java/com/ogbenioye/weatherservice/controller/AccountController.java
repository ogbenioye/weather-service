package com.ogbenioye.weatherservice.controller;

import com.ogbenioye.weatherservice.dto.ApiResponse;
import com.ogbenioye.weatherservice.dto.LoginResponse;
import com.ogbenioye.weatherservice.dto.UserDto;
import com.ogbenioye.weatherservice.model.ApplicationUser;
import com.ogbenioye.weatherservice.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ApiResponse<ApplicationUser> register(@RequestBody UserDto body) {
        return accountService.registerUser(body);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody UserDto body) {
        return accountService.login(body);
    }

    @PostMapping("/webhooks/add")
    public ApiResponse<Boolean> addWebhook(@RequestBody String webhook) {
        return accountService.configureWebhook(webhook);
    }
}
