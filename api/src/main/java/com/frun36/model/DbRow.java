package com.frun36.model;

import java.util.Map;

import com.frun36.model.factory.DbRowFactory;
import com.frun36.model.factory.PointFactory;
import com.frun36.model.factory.RouteFactory;
import com.frun36.model.factory.RoutePointFactory;
import com.frun36.model.factory.TrailFactory;
import com.frun36.model.factory.AppUserFactory;

public interface DbRow {
    Integer getId();

    Map<String, Object> getMap();

    static DbRowFactory getFactory(Class<?> c) {
        return switch (c.getSimpleName()) {
            case "Point" -> new PointFactory();
            case "Trail" -> new TrailFactory();
            case "Route" -> new RouteFactory();
            case "RoutePoint" -> new RoutePointFactory();
            case "AppUser" -> new AppUserFactory();
            default -> throw new RuntimeException("Invalid factory type " + c.getName());
        };
    }
}
