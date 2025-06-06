package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ChangePasswordRequestDto;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequestDto request, Principal connectedUser) {
        service.changePassword(request, connectedUser);
    }
}