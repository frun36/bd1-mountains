package com.frun36;

import java.sql.Timestamp;

public class Tables {
    public record Point(Integer id, String name, Integer altitude, String type) {
    }

    public record Trail(Integer id, Integer startPointId, Integer endPointId, Integer gotPoints, String color) {
    }

    public record RoutePoint(Integer id, Integer routeId, Integer currentPointId, Integer previousPointId,
            Integer nextPointId) {
    }

    public record Route(Integer id, String name, Integer userId, Timestamp timeAdded) {
    }
}
