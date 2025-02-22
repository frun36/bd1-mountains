package com.frun36.mountains.api.controller.raw;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.raw.Trail;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/raw/trail")
public class TrailController extends CrudController<Trail> {
    @Override
    protected String getTableName() {
        return "trail";
    }
}
