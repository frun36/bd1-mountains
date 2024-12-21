package com.frun36.model;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteFactory extends DbRowFactory {
    @Override
    public Route fromResult(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        Integer userId = rs.getInt("user_id");
        Timestamp timeAdded = rs.getTimestamp("time_added");

        return new Route(id, name, userId, timeAdded);
    }
}