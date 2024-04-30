package com.ElectionSurvey;

import java.sql.SQLException;

import com.ElectionSurvey.DBConfig.DatabaseInitializer;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class Application {

	

	public static void main(String[] args) throws SQLException {
		Micronaut.run(Application.class, args);
		 ApplicationContext context = ApplicationContext.run();
	        context.refresh();
	        DatabaseInitializer initializer = context.getBean(DatabaseInitializer.class);
	        initializer.initialize();
	}

}