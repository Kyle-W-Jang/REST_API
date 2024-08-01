package com.RESTAPI.KJ.health;

import com.RESTAPI.KJ.exceptions.DatabaseHealthCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

    @Component
    public class DatabaseHealthIndicator implements HealthIndicator {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Override
        public Health health() {
            try {
                checkDatabaseHealth();
                return Health.up().withDetail("Database", "Available").build();
            } catch (DatabaseHealthCheckException ex) {
                return Health.down().withDetail("Database", "Not Available").withException(ex).build();
            }
        }

        private void checkDatabaseHealth() throws DatabaseHealthCheckException {
            try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
                // Perform a simple query to check database health
                jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            } catch (SQLException ex) {
                // Throw custom exception if health check fails
                throw new DatabaseHealthCheckException("Database health check failed", ex);
            }
        }
    }

