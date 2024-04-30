package com.ElectionSurvey.DBConfig;

import io.micronaut.context.annotation.ConfigurationProperties;
import jakarta.inject.Singleton;
@Singleton
@ConfigurationProperties("datasources.default")
public class DatabaseConfig {
    private String url;
    private String urltable;
    private String driverClassName;
    private String username;
    private String password;
    private String databaseName;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrltable() {
		return urltable;
	}
	public void setUrltable(String urltable) {
		this.urltable = urltable;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
    
	
	
}
