package com.frun36.mountains.api.model.raw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public record RouteTrail(Integer id, Integer routeId, Integer trailId, Integer prevId,
        Integer nextId) implements DbRow {
    public RouteTrail(ResultSet rs) throws SQLException {
        this(
            rs.getObject("id", Integer.class),
            rs.getObject("route_id", Integer.class),
            rs.getObject("trail_id", Integer.class),
            rs.getObject("prev_id", Integer.class),
            rs.getObject("next_id", Integer.class)
        );
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("route_id", routeId);
        map.put("trail_id", trailId);
        map.put("prev_id", prevId);
        map.put("next_id", nextId);
        return map;
    }
}
