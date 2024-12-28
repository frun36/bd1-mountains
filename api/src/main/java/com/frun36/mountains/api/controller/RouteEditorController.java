package com.frun36.mountains.api.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.Route;
import com.frun36.mountains.api.model.RouteTrailInfo;
import com.frun36.mountains.api.service.RouteEditorService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/routes")
public class RouteEditorController {
    @Autowired
    RouteEditorService routeEditorService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Route>> getRoutes(@PathVariable int userId) throws DataAccessException {
        return ResponseEntity.ok().body(routeEditorService.getRoutesForUser(userId));
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<List<RouteTrailInfo>> getRoute(@PathVariable int routeId) throws DataAccessException, SQLException {
        return ResponseEntity.ok().body(routeEditorService.get(routeId));
    }

    @GetMapping("/{routeId}/appendable")
    public ResponseEntity<List<RouteTrailInfo>> getAppendable(@PathVariable int routeId) throws DataAccessException, SQLException {
        return ResponseEntity.ok().body(routeEditorService.getAppendable(routeId));
    }

    @PostMapping("/{routeId}/append")
    public ResponseEntity<Integer> append(@PathVariable int routeId, @RequestBody int trailId) throws DataAccessException {
        Integer appendResult = routeEditorService.append(routeId, trailId);
        return appendResult == null ? ResponseEntity.internalServerError().build() : ResponseEntity.ok().body(appendResult);
    }
}
