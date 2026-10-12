package com.example.appointment.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username:sa}")
    private String username;

    @Value("${spring.datasource.password:}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        String resolvedUrl = url != null ? url.trim() : "";
        String resolvedUser = username != null ? username.trim() : "";
        String resolvedPass = password != null ? password.trim() : "";

        // If URL is in standard cloud format: mysql://user:pass@host:port/db or postgres://user:pass@host:port/db
        if (resolvedUrl.startsWith("mysql://") || resolvedUrl.startsWith("postgres://") || resolvedUrl.startsWith("postgresql://")) {
            try {
                URI uri = new URI(resolvedUrl);
                String scheme = uri.getScheme().equalsIgnoreCase("postgres") ? "postgresql" : uri.getScheme().toLowerCase();
                int port = uri.getPort() != -1 ? uri.getPort() : (scheme.equals("mysql") ? 3306 : 5432);
                String path = uri.getPath();
                if (path != null && path.startsWith("/")) {
                    path = path.substring(1);
                }

                resolvedUrl = "jdbc:" + scheme + "://" + uri.getHost() + ":" + port + "/" + path;
                if (uri.getQuery() != null) {
                    resolvedUrl += "?" + uri.getQuery();
                }

                if (uri.getUserInfo() != null && !uri.getUserInfo().isEmpty()) {
                    String[] userInfo = uri.getUserInfo().split(":", 2);
                    resolvedUser = userInfo[0];
                    if (userInfo.length > 1) {
                        resolvedPass = userInfo[1];
                    }
                }
            } catch (Exception ignored) {
                // Keep original if custom parsing fails
            }
        }

        config.setJdbcUrl(resolvedUrl);
        config.setUsername(resolvedUser);
        config.setPassword(resolvedPass);
        config.setConnectionTimeout(30000);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        return new HikariDataSource(config);
    }
}

