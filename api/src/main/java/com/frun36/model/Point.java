package com.frun36.model;

import java.util.HashMap;
import java.util.Map;

public record Point(Integer id, String name, Integer altitude, String type) implements DbRow {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("name", name);
        map.put("altitude", altitude);
        map.put("type", type);
        return map;
    }
}