package com.frun36.mountains.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.RoutePoint;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/raw/route_point")
public class RoutePointController extends CrudController<RoutePoint> {
    @Override
    protected String getTableName() {
        return "route_point";
    }
}
