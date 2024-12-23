package com.frun36.mountains.api.service;

import javax.script.ScriptException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Service
public class ScriptService {
    private DataSource dataSource;

    @Autowired
    public ScriptService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public void executeSqlScript(String scriptPath) throws ScriptException {
        Resource resource = new ClassPathResource(scriptPath);

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(resource);

        databasePopulator.execute(this.dataSource);
    }
}
