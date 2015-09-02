package net.student.config;

import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

@Configuration
public class DataSourceConfig {

    
    @Bean(initMethod = "initialize", destroyMethod = "close")
    public ConnectionSource dataSource() throws SQLException {
//    	ConnectionSource dataSource = new JdbcConnectionSource("jdbc:sqlite::resource:student.db");
    	ConnectionSource dataSource = new JdbcConnectionSource("jdbc:sqlite:f:/student.db");
        return dataSource;
    }
}
