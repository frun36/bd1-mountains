package com.frun36.mountains.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.service.LoginService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping
    public ResponseEntity<Boolean> login(@RequestParam String username, @RequestParam String password) {
        Boolean loginResult = loginService.login(username, password);

        return loginResult == null ? ResponseEntity.internalServerError().build() : ResponseEntity.ok().body(loginResult);
    }
}
