package com.frun36.mountains.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.Point;
import com.frun36.mountains.api.service.PointService;

@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    private PointService pointService;

    @GetMapping
    public ResponseEntity<List<Point>> getAll() {
        List<Point> points = pointService.getAll();
        if (points.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no points exist
        }
        return ResponseEntity.ok(points); // Return 200 OK with the list of points
    }

    @GetMapping("/{id}")
    public ResponseEntity<Point> get(@PathVariable int id) {
        Point p = pointService.getById(id);
        if (p != null) {
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody Point point) {
        int createdPointId = pointService.create(point);
        return ResponseEntity.status(201).body(createdPointId); // Return only the ID of the newly created point
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Point point) {
        boolean result = pointService.update(id, point);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean isDeleted = pointService.deletePoint(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
