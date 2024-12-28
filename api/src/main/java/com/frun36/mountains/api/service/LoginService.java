package com.frun36.mountains.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean login(String username, String password) throws DataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT * FROM mountains.app_user WHERE username = ? AND password = ?)", Boolean.class,
                username, password);
    }
}
