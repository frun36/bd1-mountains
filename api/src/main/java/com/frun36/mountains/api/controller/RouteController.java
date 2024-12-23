package com.frun36.mountains.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.Route;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/raw/route")
public class RouteController extends CrudController<Route> {
    @Override
    protected String getTableName() {
        return "route";
    }
}