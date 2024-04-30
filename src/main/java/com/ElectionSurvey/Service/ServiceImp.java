package com.ElectionSurvey.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

import com.ElectionSurvey.DBConfig.DatabaseConfig;
import com.ElectionSurvey.Model.FileContent;
import com.ElectionSurvey.Model.ForgetPassword;
import com.ElectionSurvey.Model.QuestinPojo;
import com.ElectionSurvey.Model.Signuppojo;
import com.ElectionSurvey.Model.SurveyForChooseCondidate;
import com.ElectionSurvey.Model.UploadedImage;
import com.ElectionSurvey.Model.Electionpojo;
import com.ElectionSurvey.Model.Loginpojo;
import com.ElectionSurvey.Model.Sentotponmail;
import com.ElectionSurvey.Model.Show;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.json.tree.JsonObject;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ServiceImp implements ServiceInterface {
	@Inject
	ApplicationContext context1;

	@Override
	public QuestinPojo SurveyQuestionApi(QuestinPojo survey) throws IOException, SQLException, ClassNotFoundException {
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
					config.getPassword());

			stmt = conn.prepareStatement(ECQueryConstant.INSERT_INTO_DATA_VOTERS_TABLE
					.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));

			stmt.setString(1, survey.getState());
			//System.out.println("State:"+survey.getState());
			stmt.setString(2, survey.getTaluka());
			stmt.setString(3, survey.getVillage());
			stmt.setString(4, survey.getBooth_No());
			stmt.setString(5, survey.getFamily_No_for_Booth());
			stmt.setString(6, survey.getSr_No_for_HOF_in_VL());
			stmt.setString(7, survey.getCaste());
			stmt.setString(8, survey.getCategory());
			stmt.setString(9, survey.getReligion());
			stmt.setString(10, survey.getSr_No_on_VL());
			stmt.setString(11, survey.getName_of_voter());
			stmt.setString(12, survey.getAge_of_voter());
			stmt.setString(13, survey.getMobile_number());
			stmt.setString(14, survey.getAdhar_no());
			stmt.setString(15, survey.getEducation());
			stmt.setString(16, survey.getOccupation());
			stmt.setString(17, survey.getVehicle());
			stmt.setString(18, survey.getAnnual_income());
			stmt.setString(19, survey.getToilet());
			stmt.setString(20, survey.getWater_source());
			stmt.setString(21, survey.getPerson_from_whom_you_seek_help());
			stmt.setString(22, survey.getDevelopment());
			stmt.setString(23, survey.getDevelopment_description());
			stmt.setString(24, survey.getWhom_are_you_going_to_vote_for());
			stmt.setString(25, survey.getCandidate_of_choice());
			stmt.setString(26, survey.getWhich_issue_are_you_facing_currently());

			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return survey;
	}

	@Override
	public String delete(String a_no) throws SQLException, IOException, ClassNotFoundException {
		// SQL query to delete records from table
		System.out.println(a_no + "name");
		// String sql = "DELETE FROM voters WHERE adhar_no = ?"; // Use parameterized
		// query

		// Establish database connection
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
					config.getPassword());
			stmt = conn.prepareStatement(ECQueryConstant.DELETE_DATA_FROM_VOTERS_TABLE
					.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));

			// Set the parameter value
			stmt.setString(1, a_no);

			// Execute SQL query to delete records
			stmt.executeUpdate();

			// Close the PreparedStatement and database connection
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// Handle any SQL exceptions
			e.printStackTrace();
		}

		// Return a response indicating the result of the delete operation
		return "Deleted ";
	}

	@Override
	public FileContent downloadExcel() throws IOException {

		FileContent model = new FileContent();

		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
					config.getPassword());
			stmt = conn.prepareStatement(ECQueryConstant.SELECT_DATA_FROM_VOTERS_TABLE
					.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
			ResultSet resultSet = stmt.executeQuery();

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Survey");
			Row headerRow = sheet.createRow(0);

			// Create header cells

			headerRow.createCell(0).setCellValue("id");
			headerRow.createCell(1).setCellValue("state");
			headerRow.createCell(2).setCellValue("taluka");
			headerRow.createCell(3).setCellValue("village");
			headerRow.createCell(4).setCellValue("booth_No");
			headerRow.createCell(5).setCellValue("family_No_for_Booth");
			headerRow.createCell(6).setCellValue("sr_No_for_HOF_in_VL");
			headerRow.createCell(7).setCellValue("caste");
			headerRow.createCell(8).setCellValue("category");
			headerRow.createCell(9).setCellValue("religion");
			headerRow.createCell(10).setCellValue("sr_No_on_VL");
			headerRow.createCell(11).setCellValue("name_of_voter");
			headerRow.createCell(12).setCellValue("age_of_voter");
			headerRow.createCell(13).setCellValue("mobile_number");
			headerRow.createCell(14).setCellValue("adhar_no");
			headerRow.createCell(15).setCellValue("education");
			headerRow.createCell(16).setCellValue("occupation");
			headerRow.createCell(17).setCellValue("vehicle");
			headerRow.createCell(18).setCellValue("annual_income");
			headerRow.createCell(19).setCellValue("toilet");
			headerRow.createCell(20).setCellValue("water_source");
			headerRow.createCell(21).setCellValue("person_from_whom_you_seek_help");
			headerRow.createCell(22).setCellValue("development");
			headerRow.createCell(23).setCellValue("development_description");
			headerRow.createCell(24).setCellValue("whom_are_you_going_to_vote_for");
			headerRow.createCell(25).setCellValue("candidate_of_choice");
			headerRow.createCell(26).setCellValue("which_issue_are_you_facing_currently");

			int rowNum = 1;
			while (resultSet.next()) {
				// Retrieve data from the ResultSet
				Long id = resultSet.getLong("id");
				String state = resultSet.getString("state");
				String VDS = resultSet.getString("taluka");
				String village = resultSet.getString("village");
				String boothNo = resultSet.getString("booth_No");
				String Family_No_for_Booth = resultSet.getString("family_No_for_Booth");
				String Sr_No_for_HOF_in_VL = resultSet.getString("sr_No_for_HOF_in_VL");
				String caste = resultSet.getString("caste");
				String Category = resultSet.getString("category");
				String religion = resultSet.getString("religion");
				String Sr_No_on_VL = resultSet.getString("sr_No_on_VL");
				String name_of_voter = resultSet.getString("name_of_voter");
				String age_of_voter = resultSet.getString("age_of_voter");
				String mobile_number = resultSet.getString("mobile_number");
				String adhar_no = resultSet.getString("adhar_no");
				String education = resultSet.getString("education");
				String occupation = resultSet.getString("occupation");
				String vehicle = resultSet.getString("vehicle");
				String annual_income = resultSet.getString("annual_income");
				String toilet = resultSet.getString("toilet");
				String water_source = resultSet.getString("water_source");
				String Person_from_whom_you_seek_help = resultSet.getString("person_from_whom_you_seek_help");
				String development = resultSet.getString("development");
				String development_description = resultSet.getString("development_description");
				String Whom_are_you_going_to_vote_for = resultSet.getString("whom_are_you_going_to_vote_for");
				String candidate_of_choice = resultSet.getString("candidate_of_choice");
				String which_issue_are_you_facing_currently = resultSet
						.getString("which_issue_are_you_facing_currently");

				// Create a new row in the Excel sheet
				Row row = sheet.createRow(rowNum++);

				// Set cell values in the row
				row.createCell(0).setCellValue(id);
				row.createCell(1).setCellValue(state);
				row.createCell(2).setCellValue(VDS);
				row.createCell(3).setCellValue(village);
				row.createCell(4).setCellValue(boothNo);
				row.createCell(5).setCellValue(Family_No_for_Booth);
				row.createCell(6).setCellValue(Sr_No_for_HOF_in_VL);
				row.createCell(7).setCellValue(caste);
				row.createCell(8).setCellValue(Category);
				row.createCell(9).setCellValue(religion);
				row.createCell(10).setCellValue(Sr_No_on_VL);
				row.createCell(11).setCellValue(name_of_voter);
				row.createCell(12).setCellValue(age_of_voter);
				row.createCell(13).setCellValue(mobile_number);
				row.createCell(14).setCellValue(adhar_no);
				row.createCell(15).setCellValue(education);
				row.createCell(16).setCellValue(occupation);
				row.createCell(17).setCellValue(vehicle);
				row.createCell(18).setCellValue(annual_income);
				row.createCell(19).setCellValue(toilet);
				row.createCell(20).setCellValue(water_source);
				row.createCell(21).setCellValue(Person_from_whom_you_seek_help);
				row.createCell(22).setCellValue(development);
				row.createCell(23).setCellValue(development_description);
				row.createCell(24).setCellValue(Whom_are_you_going_to_vote_for);
				row.createCell(25).setCellValue(candidate_of_choice);
				row.createCell(26).setCellValue(which_issue_are_you_facing_currently);

			}

			 // Convert workbook to byte array
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			byte[] data = outputStream.toByteArray();
			System.out.println(data);
			model.setFileData(data);

			workbook.close();
			stmt.close();
			conn.close();
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String update(String ano, QuestinPojo sp) throws SQLException, IOException, ClassNotFoundException {
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());
		stmt = conn.prepareStatement(ECQueryConstant.UPDATE_DATA_FROM_VOTERS_TABLE
				.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmt.setString(1, sp.getState());
		stmt.setString(2, sp.getTaluka());
		stmt.setString(3, sp.getVillage());
		stmt.setString(4, sp.getBooth_No());
		stmt.setString(5, sp.getFamily_No_for_Booth());
		stmt.setString(6, sp.getSr_No_for_HOF_in_VL());
		stmt.setString(7, sp.getCaste());
		stmt.setString(8, sp.getCategory());
		stmt.setString(9, sp.getReligion());
		stmt.setString(10, sp.getSr_No_on_VL());
		stmt.setString(11, sp.getName_of_voter());
		stmt.setString(12, sp.getAge_of_voter());
		stmt.setString(13, sp.getMobile_number());
		stmt.setString(14, sp.getAdhar_no());
		stmt.setString(15, sp.getEducation());
		stmt.setString(16, sp.getOccupation());
		stmt.setString(17, sp.getVehicle());
		stmt.setString(18, sp.getAnnual_income());
		stmt.setString(19, sp.getToilet());
		stmt.setString(20, sp.getWater_source());
		stmt.setString(21, sp.getPerson_from_whom_you_seek_help());
		stmt.setString(22, sp.getDevelopment());
		stmt.setString(23, sp.getDevelopment_description());
		stmt.setString(24, sp.getWhom_are_you_going_to_vote_for());
		stmt.setString(25, sp.getCandidate_of_choice());
		stmt.setString(26, sp.getWhich_issue_are_you_facing_currently());
		stmt.setString(27, ano);

		// Execute the update statement
		stmt.executeUpdate();

		// Close the statement and connection
		stmt.close();
		conn.close();

		return null;
	}

	@Override
	public QuestinPojo preview(String ano) throws SQLException, IOException, ClassNotFoundException {
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());
		PreparedStatement stmtSelect = conn.prepareStatement(ECQueryConstant.SELECT_DATA_FROM_VOTERS_TABLE_PREVIEW
				.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmtSelect.setString(1, ano);
		ResultSet rs = stmtSelect.executeQuery();
		System.out.println("rs:" + rs);
		// TODO Auto-generated method stub
		QuestinPojo questinPojo = new QuestinPojo();
		while (rs.next()) {
			questinPojo.setId(rs.getLong("id"));
			questinPojo.setState(rs.getString("state"));
			questinPojo.setTaluka(rs.getString("taluka"));
			questinPojo.setVillage(rs.getString("village"));
			questinPojo.setBooth_No(rs.getString("booth_No"));
			questinPojo.setFamily_No_for_Booth(rs.getString("family_No_for_Booth"));
			questinPojo.setSr_No_for_HOF_in_VL(rs.getString("sr_No_for_HOF_in_VL"));
			questinPojo.setCaste(rs.getString("caste"));
			questinPojo.setCategory(rs.getString("category"));
			questinPojo.setReligion(rs.getString("religion"));
			questinPojo.setSr_No_on_VL(rs.getString("Sr_No_on_VL"));
			questinPojo.setName_of_voter(rs.getString("name_of_voter"));
			questinPojo.setAge_of_voter(rs.getString("age_of_voter"));
			questinPojo.setMobile_number(rs.getString("mobile_number"));
			questinPojo.setAdhar_no(rs.getString("adhar_no"));
			questinPojo.setEducation(rs.getString("education"));
			questinPojo.setOccupation(rs.getString("occupation"));
			questinPojo.setVehicle(rs.getString("vehicle"));
			questinPojo.setAnnual_income(rs.getString("annual_income"));
			questinPojo.setToilet(rs.getString("toilet"));
			questinPojo.setWater_source(rs.getString("water_source"));
			questinPojo.setPerson_from_whom_you_seek_help(rs.getString("person_from_whom_you_seek_help"));
			questinPojo.setDevelopment(rs.getString("development"));
			questinPojo.setDevelopment_description(rs.getString("development_description"));
			questinPojo.setWhom_are_you_going_to_vote_for(rs.getString("whom_are_you_going_to_vote_for"));
			questinPojo.setCandidate_of_choice(rs.getString("candidate_of_choice"));
			questinPojo.setWhich_issue_are_you_facing_currently(rs.getString("which_issue_are_you_facing_currently"));

		}

		return questinPojo;

	}

	@Override
	public FileContent downloadExcelfortaluka(String taluka) throws IOException {
		FileContent model = new FileContent();
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
					config.getPassword());
			stmt = conn.prepareStatement(
					ECQueryConstant.DOWNLOAD_DATA_TALUKA.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
			stmt.setString(1, taluka);
			ResultSet resultSet = stmt.executeQuery();

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Survey");
			Row headerRow = sheet.createRow(0);

			// Create header cells

			headerRow.createCell(0).setCellValue("id");
			headerRow.createCell(1).setCellValue("state");
			headerRow.createCell(2).setCellValue("taluka");
			headerRow.createCell(3).setCellValue("village");
			headerRow.createCell(4).setCellValue("booth_No");
			headerRow.createCell(5).setCellValue("family_No_for_Booth");
			headerRow.createCell(6).setCellValue("sr_No_for_HOF_in_VL");
			headerRow.createCell(7).setCellValue("caste");
			headerRow.createCell(8).setCellValue("category");
			headerRow.createCell(9).setCellValue("religion");
			headerRow.createCell(10).setCellValue("sr_No_on_VL");
			headerRow.createCell(11).setCellValue("name_of_voter");
			headerRow.createCell(12).setCellValue("age_of_voter");
			headerRow.createCell(13).setCellValue("mobile_number");
			headerRow.createCell(14).setCellValue("adhar_no");
			headerRow.createCell(15).setCellValue("education");
			headerRow.createCell(16).setCellValue("occupation");
			headerRow.createCell(17).setCellValue("vehicle");
			headerRow.createCell(18).setCellValue("annual_income");
			headerRow.createCell(19).setCellValue("toilet");
			headerRow.createCell(20).setCellValue("water_source");
			headerRow.createCell(21).setCellValue("person_from_whom_you_seek_help");
			headerRow.createCell(22).setCellValue("development");
			headerRow.createCell(23).setCellValue("development_description");
			headerRow.createCell(24).setCellValue("whom_are_you_going_to_vote_for");
			headerRow.createCell(25).setCellValue("candidate_of_choice");
			headerRow.createCell(26).setCellValue("which_issue_are_you_facing_currently");

			int rowNum = 1;
			while (resultSet.next()) {
				// Retrieve data from the ResultSet
				Long id = resultSet.getLong("id");
				String state = resultSet.getString("state");
				String VDS = resultSet.getString("taluka");
				String village = resultSet.getString("village");
				String boothNo = resultSet.getString("booth_No");
				String Family_No_for_Booth = resultSet.getString("family_No_for_Booth");
				String Sr_No_for_HOF_in_VL = resultSet.getString("sr_No_for_HOF_in_VL");
				String caste = resultSet.getString("caste");
				String Category = resultSet.getString("category");
				String religion = resultSet.getString("religion");
				String Sr_No_on_VL = resultSet.getString("sr_No_on_VL");
				String name_of_voter = resultSet.getString("name_of_voter");
				String age_of_voter = resultSet.getString("age_of_voter");
				String mobile_number = resultSet.getString("mobile_number");
				String adhar_no = resultSet.getString("adhar_no");
				String education = resultSet.getString("education");
				String occupation = resultSet.getString("occupation");
				String vehicle = resultSet.getString("vehicle");
				String annual_income = resultSet.getString("annual_income");
				String toilet = resultSet.getString("toilet");
				String water_source = resultSet.getString("water_source");
				String Person_from_whom_you_seek_help = resultSet.getString("person_from_whom_you_seek_help");
				String development = resultSet.getString("development");
				String development_description = resultSet.getString("development_description");
				String Whom_are_you_going_to_vote_for = resultSet.getString("whom_are_you_going_to_vote_for");
				String candidate_of_choice = resultSet.getString("candidate_of_choice");
				String which_issue_are_you_facing_currently = resultSet
						.getString("which_issue_are_you_facing_currently");

				// Create a new row in the Excel sheet
				Row row = sheet.createRow(rowNum++);

				// Set cell values in the row
				row.createCell(0).setCellValue(id);
				row.createCell(1).setCellValue(state);
				row.createCell(2).setCellValue(VDS);
				row.createCell(3).setCellValue(village);
				row.createCell(4).setCellValue(boothNo);
				row.createCell(5).setCellValue(Family_No_for_Booth);
				row.createCell(6).setCellValue(Sr_No_for_HOF_in_VL);
				row.createCell(7).setCellValue(caste);
				row.createCell(8).setCellValue(Category);
				row.createCell(9).setCellValue(religion);
				row.createCell(10).setCellValue(Sr_No_on_VL);
				row.createCell(11).setCellValue(name_of_voter);
				row.createCell(12).setCellValue(age_of_voter);
				row.createCell(13).setCellValue(mobile_number);
				row.createCell(14).setCellValue(adhar_no);
				row.createCell(15).setCellValue(education);
				row.createCell(16).setCellValue(occupation);
				row.createCell(17).setCellValue(vehicle);
				row.createCell(18).setCellValue(annual_income);
				row.createCell(19).setCellValue(toilet);
				row.createCell(20).setCellValue(water_source);
				row.createCell(21).setCellValue(Person_from_whom_you_seek_help);
				row.createCell(22).setCellValue(development);
				row.createCell(23).setCellValue(development_description);
				row.createCell(24).setCellValue(Whom_are_you_going_to_vote_for);
				row.createCell(25).setCellValue(candidate_of_choice);
				row.createCell(26).setCellValue(which_issue_are_you_facing_currently);

			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			byte[] data = outputStream.toByteArray();

			model.setFileData(data);

			workbook.close();
			stmt.close();
			conn.close();
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public QuestinPojo EQ(Electionpojo ep) throws IOException, SQLException, ClassNotFoundException {

		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());

		// SQL insert query
		String sql = "INSERT INTO survey_data (name, age, gender, mno, adno, village, taluka, district, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// Prepare the SQL statement with parameters
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, ep.getName());
		statement.setString(2, ep.getAge());
		statement.setString(3, ep.getAge());
		statement.setString(4, ep.getMno());
		statement.setString(5, ep.getAdno());
		statement.setString(6, ep.getVillage());
		statement.setString(7, ep.getTaluka());
		statement.setString(8, ep.getDistric());
		statement.setString(9, ep.getQ1());
		statement.setString(10, ep.getQ2());
		statement.setString(11, ep.getQ3());
		statement.setString(12, ep.getQ4());
		statement.setString(13, ep.getQ5());
		statement.setString(14, ep.getQ6());
		statement.setString(15, ep.getQ7());
		statement.setString(16, ep.getQ8());
		statement.setString(17, ep.getQ9());
		statement.setString(18, ep.getQ10());

		// Execute the SQL statement
		statement.executeUpdate();

		// Close the database connection
		statement.close();
		conn.close();

		return null;
	}

	@Override
	public FileContent downloadExcelEQ() throws IOException {
		FileContent model = new FileContent();
		String query = "SELECT * FROM survey_data";

		try {
			DatabaseConfig config = context1.getBean(DatabaseConfig.class);
			String dataBase = config.getDatabaseName();
			Connection conn = null;
			PreparedStatement stmt = null;

			conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
					config.getPassword());
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Survey_EQ");
			Row headerRow = sheet.createRow(0);

			// Create header cells

			headerRow.createCell(0).setCellValue("id");
			headerRow.createCell(1).setCellValue("Name");
			headerRow.createCell(2).setCellValue("Age");
			headerRow.createCell(3).setCellValue("Gender");
			headerRow.createCell(4).setCellValue("Mobile Number");
			headerRow.createCell(5).setCellValue("Adhar Number");
			headerRow.createCell(6).setCellValue("Village");
			headerRow.createCell(7).setCellValue("Taluka");
			headerRow.createCell(8).setCellValue("District");
			headerRow.createCell(9).setCellValue("Which political party do you currently support or lean towards?");
			headerRow.createCell(10)
					.setCellValue("On a scale of 1 to 10, how likely are you to vote in the upcoming election?");
			headerRow.createCell(11)
					.setCellValue("What are the top three issues that are most important to you in this election?");
			headerRow.createCell(12).setCellValue(
					"How satisfied are you with the performance of the current government at the state/national level?");
			headerRow.createCell(13).setCellValue(
					"Which leader do you believe is most capable of addressing the issues that are important to you?");
			headerRow.createCell(14)
					.setCellValue("Do you believe the economy has improved or declined in the past few years?");
			headerRow.createCell(15)
					.setCellValue("How do you feel about the current state of law and order in your area?");
			headerRow.createCell(16).setCellValue(
					"Are you satisfied with the progress made in the areas of healthcare, education, and infrastructure in your region?");
			headerRow.createCell(17)
					.setCellValue("Have you participated in any political rallies or events in the recent past?");
			headerRow.createCell(18).setCellValue(
					"How do you rate the overall performance of your Member of Parliament (MP) or Member of Legislative Assembly (MLA)?");

			int rowNum = 1;
			while (resultSet.next()) {
				// Retrieve data from the ResultSet
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				String age = resultSet.getString("age");
				String gender = resultSet.getString("gender");
				String mno = resultSet.getString("mno");
				String adno = resultSet.getString("adno");
				String village = resultSet.getString("village");
				String taluka = resultSet.getString("taluka");
				String distric = resultSet.getString("district");
				String q1 = resultSet.getString("q1");
				String q2 = resultSet.getString("q2");
				String q3 = resultSet.getString("q3");
				String q4 = resultSet.getString("q4");
				String q5 = resultSet.getString("q5");
				String q6 = resultSet.getString("q6");
				String q7 = resultSet.getString("q7");
				String q8 = resultSet.getString("q8");
				String q9 = resultSet.getString("q9");
				String q10 = resultSet.getString("q10");

				// Create a new row in the Excel sheet
				Row row = sheet.createRow(rowNum++);

				// Set cell values in the row
				row.createCell(0).setCellValue(id);
				row.createCell(1).setCellValue(name);
				row.createCell(2).setCellValue(age);
				row.createCell(3).setCellValue(gender);
				row.createCell(4).setCellValue(mno);
				row.createCell(5).setCellValue(adno);
				row.createCell(6).setCellValue(village);
				row.createCell(7).setCellValue(taluka);
				row.createCell(8).setCellValue(distric);
				row.createCell(9).setCellValue(q1);
				row.createCell(10).setCellValue(q2);
				row.createCell(11).setCellValue(q3);
				row.createCell(12).setCellValue(q4);
				row.createCell(13).setCellValue(q5);
				row.createCell(14).setCellValue(q6);
				row.createCell(15).setCellValue(q7);
				row.createCell(16).setCellValue(q8);
				row.createCell(17).setCellValue(q9);
				row.createCell(18).setCellValue(q10);

			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			byte[] data = outputStream.toByteArray();
			System.out.println(data);
			model.setFileData(data);

			workbook.close();
			statement.close();
			conn.close();
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<QuestinPojo> getalldata() throws IOException, SQLException, ClassNotFoundException, JSONException {
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());
		PreparedStatement stmtSelect = conn.prepareStatement(ECQueryConstant.SELECT_DATA_FROM_VOTERS_TABLE
				.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));

		ResultSet rs = stmtSelect.executeQuery();

		List<QuestinPojo> quetionlist = new ArrayList<>(); // List to store retrieved data

		while (rs.next()) {

			QuestinPojo questinPojo = buildData(rs);
			quetionlist.add(questinPojo);

		}

		return quetionlist;

	}

	private QuestinPojo buildData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		QuestinPojo questinPojo = new QuestinPojo();

		questinPojo.setId(rs.getLong("id"));
		questinPojo.setState(rs.getString("state"));
		questinPojo.setTaluka(rs.getString("taluka"));
		questinPojo.setVillage(rs.getString("village"));
		questinPojo.setBooth_No(rs.getString("booth_No"));
		questinPojo.setFamily_No_for_Booth(rs.getString("family_No_for_Booth"));
		questinPojo.setSr_No_for_HOF_in_VL(rs.getString("sr_No_for_HOF_in_VL"));
		questinPojo.setCaste(rs.getString("caste"));
		questinPojo.setCategory(rs.getString("category"));
		questinPojo.setReligion(rs.getString("religion"));
		questinPojo.setSr_No_on_VL(rs.getString("Sr_No_on_VL"));
		questinPojo.setName_of_voter(rs.getString("name_of_voter"));
		questinPojo.setAge_of_voter(rs.getString("age_of_voter"));
		questinPojo.setMobile_number(rs.getString("mobile_number"));
		questinPojo.setAdhar_no(rs.getString("adhar_no"));
		questinPojo.setEducation(rs.getString("education"));
		questinPojo.setOccupation(rs.getString("occupation"));
		questinPojo.setVehicle(rs.getString("vehicle"));
		questinPojo.setAnnual_income(rs.getString("annual_income"));
		questinPojo.setToilet(rs.getString("toilet"));
		questinPojo.setWater_source(rs.getString("water_source"));
		questinPojo.setPerson_from_whom_you_seek_help(rs.getString("person_from_whom_you_seek_help"));
		questinPojo.setDevelopment(rs.getString("development"));
		questinPojo.setDevelopment_description(rs.getString("development_description"));
		questinPojo.setWhom_are_you_going_to_vote_for(rs.getString("whom_are_you_going_to_vote_for"));
		questinPojo.setCandidate_of_choice(rs.getString("candidate_of_choice"));
		questinPojo.setWhich_issue_are_you_facing_currently(rs.getString("which_issue_are_you_facing_currently"));
		return questinPojo;
	}

	@Override
	public String sendotp(String email) throws IOException, SQLException, ClassNotFoundException {

		// Generate OTP
		Random rnd = new Random();
		int otpg = 100000 + rnd.nextInt(900000);

		String rs = null;
		// Send OTP to user's email
		final String senderEmail = "amolharde98@gmail.com"; // replace with your email address
		final String senderPassword = "bxvpnwevzqwiudct"; // replace with your email password
		String UserEmail = email; // using the entered username as the recipient email

		String subject = "OTP for sign up on Election Survey page of Anemoi Technologies";
		String message = "Your OTP for sign up is " + otpg;

		// send email using SMTP protocol
		Properties emailProperties = new Properties();
		emailProperties.put("mail.smtp.port", "465");
		emailProperties.put("mail.smtp.host", "smtp.gmail.com");
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
		emailProperties.put("mail.smtp.socketFactory.port", "465");
		emailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		emailProperties.put("mail.smtp.socketFactory.fallback", "false");
		Session session = Session.getDefaultInstance(emailProperties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(senderEmail, senderPassword);

			}

		});

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(senderEmail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(UserEmail));
			msg.setSubject(subject);
			msg.setText(message);

			Transport.send(msg);

			System.out.println("OTP sent to your email address.");
			rs = "OTP sent to your email address.";

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		try {

			DatabaseConfig config = context1.getBean(DatabaseConfig.class);
			String dataBase = config.getDatabaseName();
			Connection conn = null;
			PreparedStatement stmt = null;

			conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
					config.getPassword());
			Date orderDate = new Date();
			Timestamp sqlTimestamp = new Timestamp(orderDate.getTime());

			System.out.println("sqlTimestamp:" + sqlTimestamp);

			stmt = conn.prepareStatement(
					ECQueryConstant.INSERT_INTO_OTP_TABLE.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));

			stmt.setString(1, email);
			stmt.setInt(2, otpg);
			stmt.setTimestamp(3, sqlTimestamp);

			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return rs;

	}

	@Override
	public Signuppojo submit(Signuppojo sp) throws IOException, SQLException, ClassNotFoundException {

		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;
		Signuppojo s = new Signuppojo();
		String name = sp.getFullname();
		int lastSpaceIndex = name.lastIndexOf(" ");
		String trimmedName = name.substring(0, lastSpaceIndex).trim();
		System.out.println("trimmedName:" + trimmedName);

		String uniqueId = "_Anemoi";

		String username = trimmedName + uniqueId;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());

		PreparedStatement stmtSelect = conn.prepareStatement(
				ECQueryConstant.SELECT_DATA_FROM_OTP.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmtSelect.setString(1, sp.getMail_id());
		System.out.println("mail_id:" + sp.getMail_id());
		String rrs = null;
		ResultSet rs = stmtSelect.executeQuery();
		if (rs.next()) {
			int otpDb = rs.getInt("otp");
			System.out.println("otpDb:" + otpDb);
			int otpUser = sp.getOtp();
			System.out.println("otpUser:" + otpUser);
			if (otpUser == otpDb) {

				config = context1.getBean(DatabaseConfig.class);
				dataBase = config.getDatabaseName();
				conn = null;
				stmt = null;

				conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase,
						config.getUsername(), config.getPassword());

				stmt = conn.prepareStatement(ECQueryConstant.INSERT_INTO_USER_CREDEN
						.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));

				String imagepath = getpath(username);
				stmt.setString(1, sp.getFullname());

				stmt.setString(2, username);
				stmt.setString(3, sp.getPassword());
				stmt.setString(4, sp.getMail_id());
				stmt.setString(5, sp.getMobilenumber());
				// stmt.setString(6, imagepath);
				stmt.execute();

				conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase,
						config.getUsername(), config.getPassword());

				stmt = conn.prepareStatement(ECQueryConstant.SELECT_INSERT_IMAGE_DATA
						.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));

				stmt.setString(1, sp.getFullname());
				stmt.setString(2, username);
				stmt.setString(3, sp.getMail_id());
				stmt.setString(4, sp.getMobilenumber());
				stmt.setString(5, imagepath);
				stmt.execute();
				final String senderEmail = "amolharde98@gmail.com"; // replace with your email address
				final String senderPassword = "bxvpnwevzqwiudct"; // replace with your email password
				String UserEmail = sp.getMail_id(); // using the entered username as the recipient email

				String subject = "Congratulations! You're now signed up for the Anemoi Election Survey page";
				String message = "Hi\t" + username
						+ "\n\nCongratulation You Are Sign Up in EC page of Anemoi\nYour Username=" + username
						+ "\nYour Password Is=" + sp.getPassword() + "\n\nThanks and Regards\nTeam Anemoi";

				// send email using SMTP protocol
				Properties emailProperties = new Properties();
				emailProperties.put("mail.smtp.port", "465");
				emailProperties.put("mail.smtp.host", "smtp.gmail.com");
				emailProperties.put("mail.smtp.auth", "true");
				emailProperties.put("mail.smtp.starttls.enable", "true");
				emailProperties.put("mail.smtp.socketFactory.port", "465");
				emailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				emailProperties.put("mail.smtp.socketFactory.fallback", "false");
				Session session = Session.getDefaultInstance(emailProperties, new Authenticator() {

					@Override
					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication(senderEmail, senderPassword);

					}

				});

				try {
					Message msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(senderEmail));
					msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(UserEmail));
					msg.setSubject(subject);
					msg.setText(message);

					Transport.send(msg);

					System.out.println("Username and password sent to your email address.");

				} catch (MessagingException e) {
					e.printStackTrace();
				}

				System.out.println("Sign up seccessfully............");
				rrs = "Sign up seccessfully............";

			}

			else {
				System.out.println("Wrong OTP......");

				rrs = "Wrong OTP......";
			}

			stmtSelect.close();
		}
		sp.setRrs(rrs);
		return sp;
	}

	private String getpath(String username) throws SQLException {
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());

		stmt = conn.prepareStatement(ECQueryConstant.SELECT_DATA_FROM_USER_CREDEN_IMAGE
				.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmt.setString(1, username);

		System.out.println(username);
		String name = null;
		String r = null;
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			System.out.println("Amol &&&&&&&&&&&&");
			name = rs.getString("fullname");
			System.out.println(name);
		}

		String folderPath = "C:\\Profile Image\\";
		String targetFileName = name;
		System.out.println("targetFileName:" + targetFileName);

		File folder = new File(folderPath);
		System.out.println(folder);

		if (folder.exists() && folder.isDirectory()) {
			System.out.println("folder is exist");
			File[] files = folder.listFiles();
			if (files != null) {
				System.out.println("file not null");
				for (File file : files) {
					System.out.println("Files");
					String filename = file.getName();
					System.out.println("filename:" + filename);
					int dotIndex = filename.lastIndexOf(".");
					String filenameWithoutExtension = null;
					String extension = null;
					if (dotIndex > 0) {
						filenameWithoutExtension = filename.substring(0, dotIndex);
						extension = filename.substring(dotIndex + 1);

						System.out.println("Filename without extension: " + filenameWithoutExtension);
						System.out.println("Extension: " + extension);
					} else {
						System.out.println("The file does not have an extension.");
					}
					if (file.isFile() && filenameWithoutExtension.equals(targetFileName)) {
						String imagePath = file.getAbsolutePath();

						System.out.println("Image path: " + imagePath);
						r = imagePath;
						System.out.println(r);
						break; // Assuming there is only one matching file, exit the loop
					}
				}
			}
		} else {
			System.out.println("Folder does not exist or is not a directory.");
		}
		return r;
	}

	@Override
	public boolean login(Loginpojo lp) throws IOException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());

		stmt = conn.prepareStatement(
				ECQueryConstant.SELECT_DATA_FROM_USER_CREDEN.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmt.setString(1, lp.getMail_id());
		System.out.println("mail id:" + lp.getMail_id());
		System.out.println(lp.getUsername());
		boolean r = false;
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			String username = rs.getString("username");
			String password = rs.getString("password");
			System.out.println("username:" + username);

			System.out.println("password:" + password);
			String ulogin = lp.getUsername();
			System.out.println(lp.getUsername());
			String plogin = lp.getPassword();

			if (username.equals(ulogin) && (password.equals(plogin))) {
				r = true;
			} else {
				r = false;
			}

		}
		return r;

	}

	@Override
	public String forgetpassword(String username, ForgetPassword fp)
			throws IOException, SQLException, ClassNotFoundException {

		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());

		stmt = conn.prepareStatement(ECQueryConstant.SELECT_DATA_USERCREDEN_FORGET_PASS
				.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmt.setString(1, fp.getUser_name());

		ResultSet rs = stmt.executeQuery();
		String mailid = null;

		String mail_id = fp.getMail_id();
		String user_name = fp.getUser_name();
		System.out.println("mail_id:" + mail_id);
		System.out.println("user_name:" + user_name);
		String new_pass = fp.getNew_password();
		String confirm_pass = fp.getConfirm_password();

		String rmessage = null;

		while (rs.next()) {

			mailid = rs.getString("mail_id");
			username = rs.getString("username");

			System.out.println("mailid db:" + mailid);
			System.out.println("username db:" + username);

		}

		if (mail_id.equals(mailid) && username.equals(user_name) && new_pass.equals(confirm_pass)) {
			conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
					config.getPassword());

			stmt = conn.prepareStatement(ECQueryConstant.UPDATE_USERCREDEN_FOR_FORGET_PASS
					.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
			stmt.setString(1, fp.getNew_password());
			stmt.setString(2, username);
			stmt.execute();

			final String senderEmail = "amolharde98@gmail.com"; // replace with your email address
			final String senderPassword = "bxvpnwevzqwiudct"; // replace with your email password
			String UserEmail = fp.getMail_id(); // using the entered username as the recipient email

			String subject = "Changed Password Sucessfully";
			String message = "Hi\t" + fp.getUser_name()
					+ "\n\nYour Password has been Changed sucessfully.\n\n\nThanks And Regards,\nTeam Anemoi";

			// send email using SMTP protocol
			Properties emailProperties = new Properties();
			emailProperties.put("mail.smtp.port", "465");
			emailProperties.put("mail.smtp.host", "smtp.gmail.com");
			emailProperties.put("mail.smtp.auth", "true");
			emailProperties.put("mail.smtp.starttls.enable", "true");
			emailProperties.put("mail.smtp.socketFactory.port", "465");
			emailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			emailProperties.put("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getDefaultInstance(emailProperties, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {

					return new PasswordAuthentication(senderEmail, senderPassword);

				}

			});

			try {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(senderEmail));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(UserEmail));
				msg.setSubject(subject);
				msg.setText(message);

				Transport.send(msg);

			} catch (MessagingException e) {
				e.printStackTrace();
			}
			rmessage = "Password change sucessfully........";

		}

		else {
			rmessage = "Something went Wrong........";
		}

		return rmessage;
	}

	@Override
	public void saveImage(CompletedFileUpload image, Signuppojo response) throws IOException {

		String uploadDir = "C:\\Profile Image";

		createDirectoryIfNotExists(uploadDir);

		saveUploadedFile(image, uploadDir, response);
	}

	private void createDirectoryIfNotExists(String directoryPath) throws IOException {
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	private void saveUploadedFile(CompletedFileUpload fileUpload, String directory, Signuppojo response)
			throws IOException {
		String fileName = response.getFullname();
		String name = fileUpload.getFilename();
		String extension = "";

		int dotIndex = name.lastIndexOf(".");
		if (dotIndex > 0) {
			extension = name.substring(dotIndex + 1);
		}

		System.out.println("File extension: " + extension);
		String fileNameex = fileName + "." + extension;
		File targetFile = new File(directory, fileNameex);

		try (InputStream inputStream = fileUpload.getInputStream();
				FileOutputStream fileOutputStream = new FileOutputStream(targetFile)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, bytesRead);
			}
		}
	}

	@Override
	public Show showImage(String mail_id) throws IOException, SQLException, ClassNotFoundException {

		Show spp = new Show();
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());

		stmt = conn.prepareStatement(
				ECQueryConstant.SELECT_DATA_FROM_IMAGE_PATH.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmt.setString(1, mail_id);

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			spp.setFullname(rs.getString("fullname"));
			spp.setUsername(rs.getString("username"));
			spp.setMailid(rs.getString("mailid"));
			spp.setMno(rs.getString("mno"));
			spp.setImagepath(rs.getString("image_path"));

		}
		return spp;

	}

	@Override
	public String candidatechoice(SurveyForChooseCondidate sfcc)
			throws IOException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());

		stmt = conn.prepareStatement(
				ECQueryConstant.INSERT_INTO_CANDIDATE_CHOICE.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmt.setString(1, sfcc.getTaluka());
		stmt.setString(2, sfcc.getVillage());
		stmt.setString(3, sfcc.getPartyname());
		stmt.setString(4, sfcc.getCandidatename());
		stmt.setString(5, sfcc.getWhy());

		stmt.execute();
		return "Report Submit Sucessfully..........";

	}

	@Override
	public FileContent downloadCondidatechoice() throws IOException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		FileContent model = new FileContent();
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
					config.getPassword());
			stmt = conn.prepareStatement(ECQueryConstant.SELECT_DATA_FROM_CANDIDATE_CHOICE
					.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));

			ResultSet resultSet = stmt.executeQuery();

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Survey");
			Row headerRow = sheet.createRow(0);

			// Create header cells

			headerRow.createCell(0).setCellValue("id");
			headerRow.createCell(1).setCellValue("taluka");
			headerRow.createCell(2).setCellValue("village");
			headerRow.createCell(3).setCellValue("Party Name");
			headerRow.createCell(4).setCellValue("Candidate Name");
			headerRow.createCell(5).setCellValue("Why");

			int rowNum = 1;
			while (resultSet.next()) {

				Long id = resultSet.getLong("id");
				String taluka = resultSet.getString("taluka");
				String village = resultSet.getString("village");
				String partyname = resultSet.getString("partyname");
				String candidatename = resultSet.getString("candidatename");
				String why = resultSet.getString("why");

				Row row = sheet.createRow(rowNum++);

				// Set cell values in the row
				row.createCell(0).setCellValue(id);
				row.createCell(1).setCellValue(taluka);
				row.createCell(2).setCellValue(village);
				row.createCell(3).setCellValue(partyname);
				row.createCell(4).setCellValue(candidatename);
				row.createCell(5).setCellValue(why);

			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			byte[] data = outputStream.toByteArray();

			model.setFileData(data);

			workbook.close();
			stmt.close();
			conn.close();
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SurveyForChooseCondidate> showall()
			throws IOException, SQLException, ClassNotFoundException, JSONException {
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());
		PreparedStatement stmtSelect = conn.prepareStatement(ECQueryConstant.SELECT_DATA_FROM_CANDIDATE_CHOICE
				.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));

		ResultSet rs = stmtSelect.executeQuery();

		List<SurveyForChooseCondidate> quetionlist = new ArrayList<>(); // List to store retrieved data

		while (rs.next()) {

			SurveyForChooseCondidate questinPojo = buildData1(rs);
			quetionlist.add(questinPojo);
		}

		return quetionlist;

	}

	private SurveyForChooseCondidate buildData1(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		SurveyForChooseCondidate sfcc = new SurveyForChooseCondidate();
		sfcc.setId(rs.getLong("id"));
		sfcc.setTaluka(rs.getString("taluka"));
		sfcc.setVillage(rs.getString("village"));
		sfcc.setPartyname(rs.getString("partyname"));
		sfcc.setCandidatename(rs.getString("candidatename"));
		sfcc.setWhy(rs.getString("why"));

		return sfcc;
	}

	@Override
	public String updatecc(Long id, SurveyForChooseCondidate sfcc)
			throws IOException, SQLException, ClassNotFoundException, JSONException {
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());
		stmt = conn.prepareStatement(ECQueryConstant.UPDATE_DATA_FROM_CANDIDATE_CHOICE
				.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmt.setString(1, sfcc.getTaluka());
		stmt.setString(2, sfcc.getVillage());
		stmt.setString(3, sfcc.getPartyname());
		stmt.setString(4, sfcc.getCandidatename());
		stmt.setString(5, sfcc.getWhy());
		stmt.setLong(6, id);

		stmt.executeUpdate();

		stmt.close();
		conn.close();
		return "done";
	}

	@Override
	public SurveyForChooseCondidate getbyidcc(Long id) throws IOException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;

		conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
				config.getPassword());
		PreparedStatement stmtSelect = conn.prepareStatement(ECQueryConstant.SELECT_DATA_FROM_CANDIDATE_CHOICE_ID
				.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
		stmtSelect.setLong(1, id);
		ResultSet rs = stmtSelect.executeQuery();
		SurveyForChooseCondidate sfcc = new SurveyForChooseCondidate();
		while (rs.next()) {
			sfcc.setId(rs.getLong("id"));
			sfcc.setTaluka(rs.getString("taluka"));
			sfcc.setVillage(rs.getString("village"));
			sfcc.setPartyname(rs.getString("partyname"));
			sfcc.setCandidatename(rs.getString("candidatename"));
			sfcc.setWhy(rs.getString("why"));
		}
		return sfcc;
	}

	@Override
	public FileContent downloadCondidatechoicetw(String taluka)
			throws IOException, SQLException, ClassNotFoundException {
		FileContent model = new FileContent();
		DatabaseConfig config = context1.getBean(DatabaseConfig.class);
		String dataBase = config.getDatabaseName();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(config.getUrltable() + ";databaseName=" + dataBase, config.getUsername(),
					config.getPassword());
			stmt = conn.prepareStatement(ECQueryConstant.SELECT_DATA_FROM_CANDIDATE_CHOICE_TALUKA
					.replace(ECQueryConstant.DATA_BASE_PLACE_HOLDER, dataBase));
			stmt.setString(1, taluka);
			ResultSet resultSet = stmt.executeQuery();

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Survey");
			Row headerRow = sheet.createRow(0);

			// Create header cells

			headerRow.createCell(0).setCellValue("id");
			headerRow.createCell(1).setCellValue("taluka");
			headerRow.createCell(2).setCellValue("village");
			headerRow.createCell(3).setCellValue("Party Name");
			headerRow.createCell(4).setCellValue("Candidate Name");
			headerRow.createCell(5).setCellValue("Why");

			int rowNum = 1;
			while (resultSet.next()) {

				Long id = resultSet.getLong("id");
				String taluka1 = resultSet.getString("taluka");
				String village = resultSet.getString("village");
				String partyname = resultSet.getString("partyname");
				String candidatename = resultSet.getString("candidatename");
				String why = resultSet.getString("why");

				Row row = sheet.createRow(rowNum++);

				// Set cell values in the row
				row.createCell(0).setCellValue(id);
				row.createCell(1).setCellValue(taluka1);
				row.createCell(2).setCellValue(village);
				row.createCell(3).setCellValue(partyname);
				row.createCell(4).setCellValue(candidatename);
				row.createCell(5).setCellValue(why);

			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			byte[] data = outputStream.toByteArray();

			model.setFileData(data);

			workbook.close();
			stmt.close();
			conn.close();
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
