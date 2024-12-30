package com.frun36.mountains.api.controller.raw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.service.ScriptService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reset")
public class ResetController {

    private ScriptService databaseResetService;

    @Autowired
    public ResetController(ScriptService databaseResetService) {
        this.databaseResetService = databaseResetService;
    }

    @GetMapping
    public ResponseEntity<String> resetDatabase() {
        try {
            String scriptPath = "tatra.sql";
            databaseResetService.executeSqlScript(scriptPath);
            return ResponseEntity.ok("Database reset successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error performing reset: " + e.getMessage());
        }
    }
}
