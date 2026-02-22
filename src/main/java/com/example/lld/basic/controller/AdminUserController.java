package com.example.lld.basic.controller;

import com.example.lld.basic.dto.CreateUserRequest;
import com.example.lld.basic.entity.User;
import com.example.lld.basic.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}
