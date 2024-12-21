package com.frun36.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public record Route(Integer id, String name, Integer userId, Timestamp timeAdded) implements DbRow {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("name", name);
        map.put("user_id", userId);
        map.put("date_added", timeAdded);
        return map;
    }
}
