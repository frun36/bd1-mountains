package com.frun36.mountains.api.model.raw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public record Trail(Integer id, Integer startPointId, Integer endPointId, Integer gotPoints, String color)
        implements DbRow {
    public Trail(ResultSet rs) throws SQLException {
        this(
            rs.getObject("id", Integer.class),
            rs.getObject("start_point_id", Integer.class),
            rs.getObject("end_point_id", Integer.class),
            rs.getObject("got_points", Integer.class),
            rs.getString("color")
        );
    }
    
    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("start_point_id", startPointId);
        map.put("end_point_id", endPointId);
        map.put("got_points", gotPoints);
        map.put("color", color);
        return map;
    }
}