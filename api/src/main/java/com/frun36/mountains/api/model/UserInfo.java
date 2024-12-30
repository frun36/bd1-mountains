package com.frun36.mountains.api.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public record UserInfo(Integer id, Long rank, String username, Integer routeCount, BigDecimal avgRouteLen, Integer totalGotPoints) {
    public UserInfo(ResultSet rs) throws SQLException {
        this(
            rs.getObject("id", Integer.class),
            rs.getObject("r", Long.class),
            rs.getString("username"),
            rs.getObject("route_count", Integer.class),
            rs.getObject("avg_route_len", BigDecimal.class),
            rs.getObject("total_got_points", Integer.class)
        );
    }
}
