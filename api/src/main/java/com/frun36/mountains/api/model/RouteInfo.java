package com.frun36.mountains.api.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public record RouteInfo(Integer id, String name, String username, Timestamp timeModified, Integer totalGotPoints) {
    public RouteInfo(ResultSet rs) throws SQLException {
        this(rs.getObject("id", Integer.class), rs.getString("name"), rs.getString("username"), rs.getTimestamp("time_modified"),
                rs.getObject("total_got_points", Integer.class));
    }

}
