package com.ElectionSurvey.Controller;

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
import com.ElectionSurvey.Model.Electionpojo;
import com.ElectionSurvey.Model.Loginpojo;
import com.ElectionSurvey.Model.Sentotponmail;
import com.ElectionSurvey.Model.Show;
import com.ElectionSurvey.Service.ServiceInterface;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;

import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.MediaType;
import jakarta.inject.Inject;

@Controller
public class Surveyapi {
	public static final String CONTENT_DISPOSITION_VALUE = "attachment;filename=";
	@Inject
	ServiceInterface si;

	@Post("/Submit_survey_data")

	String upload_ans(@Body QuestinPojo survey) throws IOException, SQLException, ClassNotFoundException {
		System.out.println("survey:" + survey.getState());
		System.out.println(survey.getTaluka());
		si.SurveyQuestionApi(survey);

		return "submit survey";
	}

	@Delete("/delete/{ano}")
	public String deleteVoter(@PathVariable("ano") String a_no) throws ClassNotFoundException, SQLException {
		try {
			si.delete(a_no);
			return ("Voter with name " + a_no + " deleted successfully");
		} catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();

		}
		return null;

	}

//@Get("/download_excel_sheet")
//String download() throws IOException
//{
//	
//	 si.downloadExcel();
//	 
//	 return "done";
//}
	@Get("/download_all_survey_data")
	public MutableHttpResponse<byte[]> downloadFile() {
		try {
			FileContent fileBytes = this.si.downloadExcel();

			return HttpResponse.ok().body(fileBytes.getFileData())
					.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*")
					.header(HttpHeaders.CONTENT_DISPOSITION, Surveyapi.CONTENT_DISPOSITION_VALUE
							+ System.currentTimeMillis() + "AllSurveyReport" + ".xlsx");
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponse.notFound();
		}
	}

	@Get("/download_SD_Tal_wise/{taluka}")
	public MutableHttpResponse<byte[]> downloadFiletaluka(@PathVariable("taluka") String taluka) {
		try {
			FileContent fileBytes = this.si.downloadExcelfortaluka(taluka);

			return HttpResponse.ok().body(fileBytes.getFileData())
					.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*")
					.header(HttpHeaders.CONTENT_DISPOSITION, Surveyapi.CONTENT_DISPOSITION_VALUE
							+ System.currentTimeMillis() + taluka + "SurveyReport" + ".xlsx");
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponse.notFound();
		}
	}

	@Patch("/update_SD/{adhar_no}")
	String update1(@PathVariable("adhar_no") String ano, QuestinPojo sp)
			throws ClassNotFoundException, SQLException, IOException {
		si.update(ano, sp);
		return ("update" + ano + "Sucessfully");
	}

	@Get("/preview_SD/{ano}")
	QuestinPojo preview(@PathVariable String ano) throws ClassNotFoundException, SQLException, IOException {
		return si.preview(ano);

	}

	@Get("/getallsurveydata")
	List<QuestinPojo> getall() throws ClassNotFoundException, SQLException, IOException, JSONException {

		return si.getalldata();

	}


	


	@Post("/UploadEQ")

	String upload_ansEQ(@Body Electionpojo ep) throws IOException, SQLException, ClassNotFoundException {

		si.EQ(ep);

		return "submit survey";
	}

	@Get("/download_EQ")
	public MutableHttpResponse<byte[]> downloadFileEQ() {
		try {
			FileContent fileBytes = this.si.downloadExcelEQ();

			return HttpResponse.ok().body(fileBytes.getFileData())
					.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*")
					.header(HttpHeaders.CONTENT_DISPOSITION, Surveyapi.CONTENT_DISPOSITION_VALUE
							+ System.currentTimeMillis() + "EQSurveyReport" + ".xlsx");
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponse.notFound();
		}

	}

	@Get("/sent_otp_mail/{email}")
	public String sendotp(@PathVariable String email) throws IOException, SQLException, ClassNotFoundException {
		System.out.println("Email " + email);
		String response = si.sendotp(email);
		return response;

	}

	@Post(uri="/submit"  ,consumes = MediaType.MULTIPART_FORM_DATA, produces = MediaType.TEXT_PLAIN)
	 @Consumes(MediaType.MULTIPART_FORM_DATA)
	Signuppojo submitdetails(@Body Signuppojo sp,CompletedFileUpload image) throws ClassNotFoundException, IOException, SQLException {

		
		  		
		Signuppojo response = si.submit(sp);
		
		if (image != null) {
            
            si.saveImage(image,response);
        }


		return response;

	}
	
	@Get("/get_user_details/{mail_id}")
	
	Show imagepath(@PathVariable("mail_id") String emailid) throws ClassNotFoundException, IOException, SQLException
	{
		
		Show response=si.showImage(emailid);

		return response;
		
	}

	@Post("/login")
	boolean loginpage(@Body Loginpojo lp) throws ClassNotFoundException, IOException, SQLException {

		boolean response = si.login(lp);

			return response;
		
	}


	@Patch("forget_password/{username}")
	
	String fp(@PathVariable ("username")  String username,ForgetPassword fp)  throws IOException,SQLException,ClassNotFoundException
	{
		System.out.println("forget password");
		String response=si.forgetpassword(username,fp);
		
		return response;
	}
	
	@Post("/Submit_candidate_choice_survey")
	String Candidatechoicesurvey(SurveyForChooseCondidate sfcc) throws ClassNotFoundException, IOException, SQLException
	{
		String r=si.candidatechoice(sfcc);
		return r;
		
	}
	@Get("/download_all_candidate_choice")
	public MutableHttpResponse<byte[]> downloadFileCandidate() {
		try {
			FileContent fileBytes = this.si.downloadCondidatechoice();

			return HttpResponse.ok().body(fileBytes.getFileData())
					.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*")
					.header(HttpHeaders.CONTENT_DISPOSITION, Surveyapi.CONTENT_DISPOSITION_VALUE
							+ System.currentTimeMillis() + "Candidate Survey" + ".xlsx");
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponse.notFound();
		}
	}
	@Get("/get_all_cc")
	
	List<SurveyForChooseCondidate>  showall() throws ClassNotFoundException, IOException, SQLException, JSONException
	{
		return si.showall();
	}
	
	
	@Patch("/update_cc/{id}")
	String updatecc(@PathVariable("id") Long id,SurveyForChooseCondidate sfcc )
			throws ClassNotFoundException, SQLException, IOException, JSONException {
		System.out.println("update");
	String r=si.updatecc(id, sfcc);
		 return r;
		
	}
@Get("/getdatabyid/{id}")
SurveyForChooseCondidate getbyidcc(@Body Long id) throws ClassNotFoundException, IOException, SQLException
{
	SurveyForChooseCondidate sfcc=si.getbyidcc(id);
	
	return sfcc;
	
}
@Get("/download_CC_Tal_wise/{taluka}")
public MutableHttpResponse<byte[]> downloadFiletalukacc(@PathVariable("taluka") String taluka) {
	try {
		FileContent fileBytes = this.si.downloadCondidatechoicetw(taluka);

		return HttpResponse.ok().body(fileBytes.getFileData())
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*")
				.header(HttpHeaders.CONTENT_DISPOSITION, Surveyapi.CONTENT_DISPOSITION_VALUE
						+ System.currentTimeMillis() + taluka + "SurveyReport" + ".xlsx");
	} catch (Exception e) {
		e.printStackTrace();
		return HttpResponse.notFound();
	}
}

}
