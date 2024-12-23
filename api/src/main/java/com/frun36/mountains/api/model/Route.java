package com.frun36.mountains.api.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public record Route(Integer id, String name, Integer userId, Timestamp timeModified) implements DbRow {
    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("name", name);
        map.put("user_id", userId);
        map.put("time_modified", timeModified);
        return map;
    }
}
