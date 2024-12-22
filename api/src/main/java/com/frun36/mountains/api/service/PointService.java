package com.frun36.mountains.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.frun36.mountains.api.model.Point;

@Service
public class PointService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Point> getAll() {
        String sql = "SELECT * FROM mountains.point";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Point(rs.getInt("id"), rs.getString("name"), rs.getInt("altitude"), rs.getString("type"));
        });
    }

    public Point getById(int id) {
        String sql = "SELECT * FROM mountains.point WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            return new Point(rs.getInt("id"), rs.getString("name"), rs.getInt("altitude"), rs.getString("type"));
        }, id);
    }

    public Integer create(Point point) {
        String sql = "INSERT INTO mountains.point (name, altitude, type) " +
                     "VALUES (?, ?, ?) RETURNING id"; // PostgreSQL specific syntax
        return jdbcTemplate.queryForObject(sql, Integer.class, point.name(), point.altitude(), point.type());
    }

    public boolean update(int id, Point point) {
        String sql = "UPDATE mountains.point SET name = ?, altitude = ?, type = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, point.name(), point.altitude(), point.type(), id);
        return rowsUpdated > 0;
    }

    public boolean deletePoint(int id) {
        String sql = "DELETE FROM mountains.point WHERE id = ?";
        int rowsDeleted = jdbcTemplate.update(sql, id);
        return rowsDeleted > 0;
    }
}
