package com.frun36.mountains.api.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record RouteBrief(Integer routeId, String name, Integer userId, String username) {
    public RouteBrief(ResultSet rs) throws SQLException {
        this(rs.getInt("route_id"), rs.getString("name"), rs.getInt("user_id"), rs.getString("username"));
    }
}
