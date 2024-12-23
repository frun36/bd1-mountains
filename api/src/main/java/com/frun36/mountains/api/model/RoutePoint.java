package com.frun36.mountains.api.model;

import java.util.HashMap;
import java.util.Map;

public record RoutePoint(Integer id, Integer routeId, Integer currentPointId, Integer previousPointId,
        Integer nextPointId) implements DbRow {
    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("route_id", routeId);
        map.put("current_point_id", currentPointId);
        map.put("previous_point_id", previousPointId);
        map.put("next_point_id", nextPointId);
        return map;
    }
}
