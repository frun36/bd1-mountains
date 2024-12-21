package com.frun36.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrailFactory extends DbRowFactory {
    @Override
    public Trail fromResult(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        Integer startPointId = rs.getInt("start_point_id");
        Integer endPointId = rs.getInt("end_point_id");
        Integer gotPoints = rs.getInt("got_points");
        String color = rs.getString("color");

        return new Trail(id, startPointId, endPointId, gotPoints, color);
    }
}
