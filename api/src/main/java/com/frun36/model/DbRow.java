package com.frun36.model;

import java.util.Map;

public interface DbRow {
    Integer getId();

    Map<String, Object> getMap();

    static DbRowFactory getFactory(Class<?> c) {
        return switch (c.getSimpleName()) {
            case "Point" -> new PointFactory();
            case "Trail" -> new TrailFactory();
            case "Route" -> new RouteFactory();
            case "RoutePoint" -> new RoutePointFactory();
            default -> throw new RuntimeException("Invalid factory type " + c.getName());
        };
    }
}
