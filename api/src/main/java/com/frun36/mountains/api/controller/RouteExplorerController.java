package com.frun36.mountains.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.RouteBrief;
import com.frun36.mountains.api.service.RouteExplorerService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/route_explorer")
public class RouteExplorerController {
    @Autowired
    RouteExplorerService routeExplorerService;

    @GetMapping
    public ResponseEntity<List<RouteBrief>> explore(@RequestParam String filter) {
        List<RouteBrief> response = switch (filter) {
            case "user_longest" -> routeExplorerService.userLongestRoutes();
            case "highest_2000" -> routeExplorerService.routesOver2k();
            case "three_colors" -> routeExplorerService.routesContaining3Colors();
            default -> null;
        };

        return response == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok().body(response);
    }
}
