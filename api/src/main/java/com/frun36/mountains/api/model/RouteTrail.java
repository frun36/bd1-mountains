package com.frun36.mountains.api.model;

import java.util.HashMap;
import java.util.Map;

public record RouteTrail(Integer id, Integer routeId, Integer trailId, Integer prevId,
        Integer nextId) implements DbRow {
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
