package org.acme.it;

import java.util.Collections;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class DatasourceResource implements QuarkusTestResourceLifecycleManager {
    
    private static final String DATABASE_USER = "devops";
    private static final String DATABASE_PASS = "password";
    private static final String DATABASE_NAME = "devops_test";
    
    static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName(DATABASE_NAME)
            .withUsername(DATABASE_USER)
            .withPassword(DATABASE_PASS);
    
    @Override
    public Map<String, String> start() {
        db.start();
        return Collections.singletonMap("quarkus.datasource.jdbc.url", db.getJdbcUrl());
    }

    @Override
    public void stop() {
        db.close();
        
    }

}
