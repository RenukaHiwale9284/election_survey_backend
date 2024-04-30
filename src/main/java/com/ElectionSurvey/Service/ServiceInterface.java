package com.ElectionSurvey.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ElectionSurvey.Model.FileContent;
import com.ElectionSurvey.Model.ForgetPassword;
import com.ElectionSurvey.Model.QuestinPojo;
import com.ElectionSurvey.Model.Signuppojo;
import com.ElectionSurvey.Model.SurveyForChooseCondidate;
import com.ElectionSurvey.Model.UploadedImage;

import io.micronaut.http.multipart.CompletedFileUpload;

import com.ElectionSurvey.Model.Electionpojo;
import com.ElectionSurvey.Model.Loginpojo;
import com.ElectionSurvey.Model.Sentotponmail;
import com.ElectionSurvey.Model.Show;


public interface ServiceInterface {

	QuestinPojo SurveyQuestionApi(QuestinPojo survey) throws IOException, SQLException, ClassNotFoundException;

	//String createTable() throws SQLException, ClassNotFoundException;

	String delete(String a_no) throws SQLException, IOException, ClassNotFoundException;

	FileContent downloadExcel() throws IOException;

	FileContent downloadExcelfortaluka(String taluka) throws IOException;

	String update(String ano, QuestinPojo sp) throws SQLException, IOException, ClassNotFoundException;

	QuestinPojo preview(String ano) throws SQLException, IOException, ClassNotFoundException;
	
	//String createTableEQ() throws SQLException, ClassNotFoundException;
	
	QuestinPojo EQ(Electionpojo ep) throws IOException, SQLException, ClassNotFoundException;
	
	FileContent downloadExcelEQ() throws IOException;
	
	List<QuestinPojo> getalldata() throws IOException, SQLException, ClassNotFoundException, JSONException;
	

	String sendotp(String email) throws IOException, SQLException, ClassNotFoundException;

	//String sendotp(String som) throws IOException, SQLException, ClassNotFoundException;

	
	
	Signuppojo submit(Signuppojo sp) throws IOException, SQLException, ClassNotFoundException;
	
	 public void saveImage(CompletedFileUpload image,Signuppojo response) throws IOException;
	
	
	boolean login(Loginpojo lp) throws IOException, SQLException, ClassNotFoundException;
	
	// public String uploadImage(CompletedFileUpload fileUpload) throws SQLException;
	
	
	String forgetpassword(String username,ForgetPassword fp)  throws IOException,SQLException,ClassNotFoundException;
	
	
	Show showImage(String mailid)  throws IOException,SQLException,ClassNotFoundException;
	
	
	
	String candidatechoice(SurveyForChooseCondidate sfcc)   throws IOException,SQLException,ClassNotFoundException;
	
	
	FileContent downloadCondidatechoice() throws IOException,SQLException,ClassNotFoundException;
	
	
	List<SurveyForChooseCondidate>  showall()  throws IOException, SQLException, ClassNotFoundException, JSONException;
	
	
	String updatecc(Long id,SurveyForChooseCondidate sfcc) throws IOException, SQLException, ClassNotFoundException, JSONException;
	
	SurveyForChooseCondidate getbyidcc(Long id) throws IOException, SQLException, ClassNotFoundException;
	
	FileContent downloadCondidatechoicetw(String taluka) throws IOException,SQLException,ClassNotFoundException;
	
}
