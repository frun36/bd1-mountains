package com.frun36.mountains.api.model.raw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public record Point(Integer id, String name, Integer altitude, String type) implements DbRow {
    public Point(ResultSet rs) throws SQLException {
        this(
            rs.getObject("id", Integer.class),
            rs.getString("name"),
            rs.getObject("altitude", Integer.class),
            rs.getString("type")
        );
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("name", name);
        map.put("altitude", altitude);
        map.put("type", type);
        return map;
    }
}
