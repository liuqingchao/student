package net.student.config;

import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

@Configuration
public class DataSourceConfig {

    
    @Bean(initMethod = "initialize", destroyMethod = "close")
    public ConnectionSource dataSource() throws SQLException {
        JdbcPooledConnectionSource dataSource = new JdbcPooledConnectionSource("jdbc:sqlite::resource:student.db");
        dataSource.setMaxConnectionAgeMillis(5 * 60 * 1000);
        return dataSource;
    }
}
