package com.frun36.mountains.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.service.LoginService;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ResponseEntity<Integer> login(@RequestParam String username, @RequestParam String password) throws DataAccessException {
        Integer loginResult = loginService.login(username, password);
        return ResponseEntity.ok().body(loginResult);
    }

    @GetMapping("/register")
    public ResponseEntity<Integer> register(@RequestParam String username, @RequestParam String password) throws DataAccessException {
        Integer loginResult = loginService.register(username, password);
        return ResponseEntity.ok().body(loginResult);
    }
}
