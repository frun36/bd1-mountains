package com.frun36.mountains.api.model.raw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public record Route(Integer id, String name, Integer userId, Timestamp timeModified, Integer totalGotPoints) implements DbRow {
    public Route(ResultSet rs) throws SQLException {
        this(
            rs.getObject("id", Integer.class),
            rs.getString("name"),
            rs.getObject("user_id", Integer.class),
            rs.getTimestamp("time_modified"),
            rs.getObject("total_got_points", Integer.class)
        );
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("name", name);
        map.put("user_id", userId);
        map.put("time_modified", timeModified);
        map.put("total_got_points", totalGotPoints);
        return map;
    }
}
