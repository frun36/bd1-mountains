package com.frun36.mountains.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.RouteInfo;
import com.frun36.mountains.api.model.UserInfo;
import com.frun36.mountains.api.service.UserInfoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/leaderboard")
    public ResponseEntity<List<UserInfo>> getLeaderboard(@RequestParam String orderBy) {
        return ResponseEntity.ok().body(userInfoService.getLeaderboard(orderBy));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getInfo(@PathVariable int userId) throws DataAccessException {
        return ResponseEntity.ok().body(userInfoService.getInfo(userId));
    }

    @GetMapping("/{userId}/routes")
    public ResponseEntity<List<RouteInfo>> getRoutes(@PathVariable int userId) throws DataAccessException {
        return ResponseEntity.ok().body(userInfoService.getRoutes(userId));
    }
}
