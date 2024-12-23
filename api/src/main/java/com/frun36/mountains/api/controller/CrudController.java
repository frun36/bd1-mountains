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

import com.frun36.mountains.api.model.DbRow;
import com.frun36.mountains.api.service.CrudService;

public abstract class CrudController<T extends DbRow> {

    abstract protected String getTableName();

    @Autowired
    private CrudService crudService;

    @GetMapping
    public ResponseEntity<List<DbRow>> getAll() {
        List<DbRow> items = crudService.getAll(getTableName());
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DbRow> get(@PathVariable int id) {
        DbRow item = crudService.getById(getTableName(), id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody T item) {
        Integer createdItemId = crudService.create(getTableName(), item);
        if (createdItemId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.status(201).body(createdItemId);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody T item) {
        boolean result = crudService.update(getTableName(), id, item);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean isDeleted = crudService.delete(getTableName(), id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
