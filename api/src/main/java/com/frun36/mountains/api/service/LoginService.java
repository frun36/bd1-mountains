package com.frun36.mountains.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer login(String username, String password) throws DataAccessException {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT id FROM mountains.app_user WHERE username = ? AND password = ?", Integer.class,
                    username, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer register(String username, String password) throws DataAccessException {
        return jdbcTemplate.queryForObject(
                "INSERT INTO mountains.app_user (username, password) VALUES (?, ?) RETURNING id",
                Integer.class, username, password);
    }
}
