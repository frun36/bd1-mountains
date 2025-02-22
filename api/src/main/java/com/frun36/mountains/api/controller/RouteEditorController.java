package com.frun36.mountains.api.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.RouteInfo;
import com.frun36.mountains.api.model.RouteTrailInfo;
import com.frun36.mountains.api.model.raw.Route;
import com.frun36.mountains.api.service.RouteEditorService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/routes")
public class RouteEditorController {
    @Autowired
    RouteEditorService routeEditorService;

    @GetMapping("/{routeId}/info")
    public ResponseEntity<RouteInfo> getRouteInfo(@PathVariable int routeId) throws DataAccessException, SQLException {
        return ResponseEntity.ok().body(routeEditorService.getInfo(routeId));
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<List<RouteTrailInfo>> getRoute(@PathVariable int routeId) throws DataAccessException, SQLException {
        return ResponseEntity.ok().body(routeEditorService.get(routeId));
    }

    @PostMapping
    public ResponseEntity<Integer> createRoute(@RequestBody Route r) throws DataAccessException {
        return ResponseEntity.ok().body(routeEditorService.addRoute(r.userId(), r.name()));
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Integer> deleteRoute(@PathVariable int routeId) throws DataAccessException {
        return ResponseEntity.ok().body(routeEditorService.deleteRoute(routeId));
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

    @DeleteMapping("/{routeId}/pop_back")
    public ResponseEntity<Integer> popBack(@PathVariable int routeId) throws DataAccessException {
        Integer deletedCount = routeEditorService.popBack(routeId);
        return deletedCount == null ? ResponseEntity.internalServerError().build() : ResponseEntity.ok().body(deletedCount);
    }

    @GetMapping("/{routeId}/prependable")
    public ResponseEntity<List<RouteTrailInfo>> getPrependable(@PathVariable int routeId) throws DataAccessException, SQLException {
        return ResponseEntity.ok().body(routeEditorService.getPrependable(routeId));
    }

    @PostMapping("/{routeId}/prepend")
    public ResponseEntity<Integer> prepend(@PathVariable int routeId, @RequestBody int trailId) throws DataAccessException {
        Integer prependResult = routeEditorService.prepend(routeId, trailId);
        return prependResult == null ? ResponseEntity.internalServerError().build() : ResponseEntity.ok().body(prependResult);
    }

    @DeleteMapping("/{routeId}/pop_front")
    public ResponseEntity<Integer> popFront(@PathVariable int routeId) throws DataAccessException {
        Integer deletedCount = routeEditorService.popFront(routeId);
        return deletedCount == null ? ResponseEntity.internalServerError().build() : ResponseEntity.ok().body(deletedCount);
    }
}
