package com.frun36.mountains.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.RouteTrail;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/raw/route_trail")
public class RouteTrailController extends CrudController<RouteTrail> {
    @Override
    protected String getTableName() {
        return "route_trail";
    }
}