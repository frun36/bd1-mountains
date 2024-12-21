package com.frun36.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PointFactory extends DbRowFactory {
    @Override
    public Point fromResult(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name"); 
        Integer altitude = rs.getInt("altitude");
        String type = rs.getString("type");

        return new Point(id, name, altitude, type);
    }
}
