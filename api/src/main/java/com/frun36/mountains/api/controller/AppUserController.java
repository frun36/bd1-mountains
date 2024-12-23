package com.frun36.mountains.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frun36.mountains.api.model.AppUser;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/raw/app_user")
public class AppUserController extends CrudController<AppUser> {
    @Override
    protected String getTableName() {
        return "app_user";
    }
}
