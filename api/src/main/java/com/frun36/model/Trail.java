package com.frun36.model;

import java.util.HashMap;
import java.util.Map;

public record Trail(Integer id, Integer startPointId, Integer endPointId, Integer gotPoints, String color)
        implements DbRow {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("start_point_id", startPointId);
        map.put("end_point_id", endPointId);
        map.put("got_points", gotPoints);
        map.put("color", color);
        return map;
    }
}