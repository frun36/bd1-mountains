package com.frun36.mountains.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.frun36.mountains.api.model.DbRow;

@Service
public class CrudService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DbRow> getAll(String tableName) {
        String sql = String.format("SELECT * FROM mountains.%s", tableName);
        return jdbcTemplate.query(sql, (rs, rowNum) -> DbRow.parseResultSet(rs, tableName));
    }

    public DbRow getById(String tableName,  int id) {
        String sql = String.format("SELECT * FROM mountains.%s WHERE id = ?", tableName);
        List<DbRow> result = jdbcTemplate.query(sql, (rs, rowNum) -> DbRow.parseResultSet(rs, tableName), id);

        if (result.size() == 1)
            return result.get(0);
        else
            return null;
    }

    public Integer create(String tableName, DbRow item) {
        List<String> names = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        for (var entry : item.asMap().entrySet()) {
            if (entry.getValue() == null)
                continue;
            
            names.add(entry.getKey());
            values.add(entry.getValue());
        }

        if (names.isEmpty() || values.isEmpty())
            return null;

        String namesSql = names.stream().collect(Collectors.joining(", "));
        String placeholders = names.stream().map(n -> "?").collect(Collectors.joining(", "));

        String sql = String.format("INSERT INTO mountains.%s (%s) VALUES (%s) RETURNING id", tableName, namesSql, placeholders);
        return jdbcTemplate.queryForObject(sql, Integer.class, values.toArray());
    }

    public boolean update(String tableName, int id, DbRow item) {
        List<String> names = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        for (var entry : item.asMap().entrySet()) {
            if (entry.getValue() == null || entry.getKey() == "id")
                continue;
            
            names.add(entry.getKey());
            values.add(entry.getValue());
        }

        values.add(id);

        String queryParams = names.stream().map(n -> String.format("%s = ?", n)).collect(Collectors.joining(", "));

        String sql = String.format("UPDATE mountains.point SET %s WHERE id = ?", queryParams);
        int rowsUpdated = jdbcTemplate.update(sql, values.toArray());
        return rowsUpdated > 0;
    }

    public boolean delete(String tableName, int id) {
        String sql = String.format("DELETE FROM mountains.%s WHERE id = ?", tableName);
        int rowsDeleted = jdbcTemplate.update(sql, id);
        return rowsDeleted > 0;
    }
}