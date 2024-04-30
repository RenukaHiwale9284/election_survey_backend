package com.ElectionSurvey.DBConfig;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import io.micronaut.context.ApplicationContext;
import jakarta.inject.Inject;

import java.sql.SQLException;

public class DatabaseInitializer {

	@Inject
	ApplicationContext context;

//    public static void main(String[] args) throws SQLException {
//        ApplicationContext context = ApplicationContext.run();
//        context.refresh();
//        DatabaseInitializer initializer = context.getBean(DatabaseInitializer.class);
//        initializer.initialize();
//    }

	public void initialize() throws SQLException {
		DatabaseConfig config = context.getBean(DatabaseConfig.class);
		System.out.println("Database URL: " + config.getUrl());

		// check if database exists
		try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUsername(),
				config.getPassword())) {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getCatalogs();
			boolean databaseExists = false;
			while (rs.next()) {
				String dbName = rs.getString("TABLE_CAT");
				// System.out.println("dbName:"+dbName);
				if (config.getDatabaseName().equalsIgnoreCase(dbName)) {
					databaseExists = true;
					break;
				}
			}
			rs.close();
			if (databaseExists) {
				System.out.println("Database already exists, skipping creation.");

				createTable();

			} else {
				System.out.println("Creating database...");
				Statement stmt = conn.createStatement();
				String sql = "CREATE DATABASE " + config.getDatabaseName();
				System.out.println("sql:" + sql);
				stmt.executeUpdate(sql);
				// add table creation or any other initialization steps here
				// create table
				createTable();

			}
		}

	}

	private void createTable() throws SQLException {
		DatabaseConfig config = context.getBean(DatabaseConfig.class);
		try (Connection conn = DriverManager.getConnection(config.getUrltable(), config.getUsername(),
				config.getPassword())) {
			DatabaseMetaData metaData = conn.getMetaData();
			List<String> tables = Arrays.asList("otp", "voters", "usercreden", "image", "candidatechoice");
			for (String tableName : tables) {
				ResultSet rs = metaData.getTables(config.getDatabaseName(), null, tableName, null);
				if (rs.next()) {
					System.out.println("Table " + tableName + " already exists, skipping creation.");
				} else {
					System.out.println("Creating table " + tableName + "...");
					String sql = "";
					switch (tableName) {
					case "voters":
						sql = "CREATE TABLE election_Servey.dbo.voters(" + "id int IDENTITY," + "state VARCHAR(50),"
								+ "taluka VARCHAR(50)," + "village VARCHAR(50)," + "booth_No VARCHAR(50),"
								+ "family_No_for_Booth VARCHAR(50)," + "sr_No_for_HOF_in_VL VARCHAR(50),"
								+ "caste VARCHAR(50)," + "category VARCHAR(50)," + "religion VARCHAR(50),"
								+ "Sr_No_on_VL VARCHAR(50)," + "name_of_voter VARCHAR(50),"
								+ "age_of_voter VARCHAR(50)," + "mobile_number VARCHAR(50)," + "adhar_no VARCHAR(50),"
								+ "education VARCHAR(50)," + "occupation VARCHAR(50)," + "vehicle VARCHAR(50),"
								+ "annual_income VARCHAR(50)," + "toilet VARCHAR(50)," + "water_source VARCHAR(50),"
								+ "person_from_whom_you_seek_help VARCHAR(50)," + "development VARCHAR(50),"
								+ "development_description VARCHAR(50)," + "whom_are_you_going_to_vote_for VARCHAR(50),"
								+ "candidate_of_choice VARCHAR(50),"
								+ "which_issue_are_you_facing_currently VARCHAR(50))";
						break;
					case "otp":
						sql = "CREATE TABLE election_Servey.dbo.otp(" + "user_id int IDENTITY,"
								+ "mail_id varchar(255)," + "otp int," + "order_date DATETIME NOT NULL)";
						break;
					case "usercreden":
						sql = "CREATE TABLE election_Servey.dbo.usercreden(" + "user_id int IDENTITY,"
								+ "fullname varchar(255)," + "username varchar(255)," + "password varchar(255),"
								+ "mail_id varchar(255)," + "mno varchar(255))";

						break;
					case "image":
						sql = "CREATE TABLE election_Servey.dbo.image(" + "id int IDENTITY," + "fullname varchar(255),"
								+ "username varchar(255)," + "mailid varchar(255)," + "mno varchar(255),"
								+ "image_path varchar(255))";
						break;
					case "candidatechoice":
						sql = "CREATE TABLE election_Servey.dbo.candidatechoice(" + "id int IDENTITY,"
								+ "taluka varchar(255)," + "village varchar(255)," + "partyname varchar(255),"
								+ "candidatename varchar(255)," + "why varchar(255))";

						break;
					}
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					System.out.println("Table " + tableName + " created successfully.");
				}
			}
		}
	}

}
