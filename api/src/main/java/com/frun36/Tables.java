package com.frun36;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Tables {
    public interface DbRow {
        Integer getId();
        Map<String, Object> getMap();
    }

    public record Point(Integer id, String name, Integer altitude, String type) implements DbRow {
        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public Map<String, Object> getMap() {
            Map<String, Object> map =  new HashMap<String, Object>();
            map.put("id", id);
            map.put("name", name);
            map.put("altitude", altitude);
            map.put("type", type);
            return map;
        }
    }

    public record Trail(Integer id, Integer startPointId, Integer endPointId, Integer gotPoints, String color)
            implements DbRow {
        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public Map<String, Object> getMap() {
            Map<String, Object> map =  new HashMap<String, Object>();
            map.put("id", id);
            map.put("start_point_id", startPointId);
            map.put("end_point_id", endPointId);
            map.put("got_points", gotPoints);
            map.put("color", color);
            return map;
        }
    }

    public record RoutePoint(Integer id, Integer routeId, Integer currentPointId, Integer previousPointId,
            Integer nextPointId) implements DbRow {
        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public Map<String, Object> getMap() {
            Map<String, Object> map =  new HashMap<String, Object>();
            map.put("id", id);
            map.put("route_id", routeId);
            map.put("current_point_id", currentPointId);
            map.put("previous_point_id", previousPointId);
            map.put("next_point_id", nextPointId);
            return map;
        }
    }

    public record Route(Integer id, String name, Integer userId, Timestamp timeAdded) implements DbRow {
        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public Map<String, Object> getMap() {
            Map<String, Object> map =  new HashMap<String, Object>();
            map.put("id", id);
            map.put("name", name);
            map.put("user_id", userId);
            map.put("date_added", timeAdded);
            return map;
        }
    }
}
