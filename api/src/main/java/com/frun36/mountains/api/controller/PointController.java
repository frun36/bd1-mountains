package com.frun36.mountains.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.Point;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/raw/point")
public class PointController extends CrudController<Point> {
    @Override
    protected String getTableName() {
        return "point";
    }
}