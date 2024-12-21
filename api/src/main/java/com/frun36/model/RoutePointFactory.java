package com.frun36.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoutePointFactory extends DbRowFactory {
    @Override
    public RoutePoint fromResult(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        Integer routeId = rs.getInt("route_id");
        Integer currentPointId = rs.getInt("current_point_id");
        Integer previousPointId = rs.getInt("previous_point_id");
        Integer nextPointId = rs.getInt("next_point_id");

        return new RoutePoint(id, routeId, currentPointId, previousPointId, nextPointId);
    }
}
