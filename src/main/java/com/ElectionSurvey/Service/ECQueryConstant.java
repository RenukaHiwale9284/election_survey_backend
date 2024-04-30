package com.ElectionSurvey.Service;

public class ECQueryConstant {

	public static final String DATA_BASE_PLACE_HOLDER = "#$DataBaseName#$";

	public static final String INSERT_INTO_DATA_VOTERS_TABLE = "INSERT INTO #$DataBaseName#$.dbo.voters values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String SELECT_DATA_FROM_VOTERS_TABLE = "SELECT * FROM #$DataBaseName#$.dbo.voters";

	public static final String UPDATE_DATA_FROM_VOTERS_TABLE = "UPDATE #$DataBaseName#$.dbo.voters SET state =?,taluka = ?,village =?,booth_No =?, family_No_for_Booth = ?,sr_No_for_HOF_in_VL = ?, caste = ?, category = ?, religion = ?, sr_No_on_VL = ?,name_of_voter = ?, age_of_voter = ?, mobile_number = ?, adhar_no = ?, education = ?,occupation = ?, vehicle = ?, annual_income = ?, toilet = ?, water_source = ?, person_from_whom_you_seek_help = ?, development = ?, development_description = ?,whom_are_you_going_to_vote_for = ?,candidate_of_choice = ?,which_issue_are_you_facing_currently = ? WHERE adhar_no = ?";

	public static final String SELECT_DATA_FROM_VOTERS_TABLE_PREVIEW = "SELECT * FROM #$DataBaseName#$.dbo.voters WHERE adhar_no =?";

	public static final String DELETE_DATA_FROM_VOTERS_TABLE = "DELETE FROM #$DataBaseName#$.dbo.voters WHERE adhar_no = ?";

	public static final String DOWNLOAD_DATA_TALUKA = "SELECT * FROM  #$DataBaseName#$.dbo.voters WHERE taluka = ?";

	public static final String INSERT_INTO_OTP_TABLE = "INSERT INTO #$DataBaseName#$.dbo.otp values(?,?,?)";

	// public static final String SELECT_DATA_FROM_OTP="SELECT * FROM
	// #$DataBaseName#$.dbo.otp WHERE mail_id=? ORDER BY date_created DESC";

	public static final String SELECT_DATA_FROM_OTP = "SELECT * FROM #$DataBaseName#$.dbo.otp WHERE mail_id=? ORDER BY order_date DESC";

	public static final String INSERT_INTO_USER_CREDEN = "INSERT INTO #$DataBaseName#$.dbo.usercreden values(?, ?, ?, ?, ?)";

	public static final String SELECT_DATA_FROM_USER_CREDEN = "SELECT * FROM #$DataBaseName#$.dbo.usercreden WHERE mail_id=?";
	
	public static final String SELECT_INSERT_IMAGE_DATA = "INSERT INTO #$DataBaseName#$.dbo.image values(?, ?,?,?,?)";
	
	public static final String SELECT_DATA_FROM_IMAGE = "SELECT * FROM #$DataBaseName#$.dbo.image WHERE id=?";
	
	public static final String SELECT_DATA_USERCREDEN_FORGET_PASS = "SELECT * FROM #$DataBaseName#$.dbo.usercreden  WHERE username=?";
	
	public static final String UPDATE_USERCREDEN_FOR_FORGET_PASS = "UPDATE #$DataBaseName#$.dbo.usercreden SET password = ? WHERE username = ?;";
	
	public static final String SELECT_DATA_FROM_USER_CREDEN_IMAGE = "SELECT * FROM  #$DataBaseName#$.dbo.usercreden WHERE username=?";
	
	public static final String SELECT_DATA_FROM_IMAGE_PATH = "SELECT * FROM #$DataBaseName#$.dbo.image WHERE mailid=?";
	
	public static final String INSERT_INTO_CANDIDATE_CHOICE = "INSERT INTO #$DataBaseName#$.dbo.candidatechoice values(?,?,?,?,?)";
	
	public static final String SELECT_DATA_FROM_CANDIDATE_CHOICE = "SELECT * FROM #$DataBaseName#$.dbo.candidatechoice";
	
	public static final String UPDATE_DATA_FROM_CANDIDATE_CHOICE = "UPDATE #$DataBaseName#$.dbo.candidatechoice SET taluka =?,village = ?,partyname=?,candidatename=?,why=? WHERE id=?";	
	
	public static final String SELECT_DATA_FROM_CANDIDATE_CHOICE_ID = "SELECT * FROM #$DataBaseName#$.dbo.candidatechoice WHERE id=?";
	
	public static final String SELECT_DATA_FROM_CANDIDATE_CHOICE_TALUKA = "SELECT * FROM #$DataBaseName#$.dbo.candidatechoice WHERE taluka=?";
	
}
