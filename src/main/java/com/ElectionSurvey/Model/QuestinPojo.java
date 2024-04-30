package com.ElectionSurvey.Model;

public class QuestinPojo {
	private Long id;
	private String state;
	private String taluka;
	private String village;
	private String booth_No;
	private String family_No_for_Booth;
	private String sr_No_for_HOF_in_VL;
	private String caste;
	private String category;
	private String religion;
	private String Sr_No_on_VL;
	private String name_of_voter;
	private String age_of_voter;
	private String mobile_number;
	private String adhar_no;
	private String education;
	private String occupation;
	private String vehicle;
	private String annual_income;
	private String toilet;
	private String water_source;
	private String person_from_whom_you_seek_help;
	private String development;
	private String development_description;
	private String whom_are_you_going_to_vote_for;
	private String candidate_of_choice;
	private String which_issue_are_you_facing_currently;

	public QuestinPojo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestinPojo(Long id, String state, String taluka, String village, String booth_No,
			String family_No_for_Booth, String sr_No_for_HOF_in_VL, String caste, String category, String religion,
			String sr_No_on_VL, String name_of_voter, String age_of_voter, String mobile_number, String adhar_no,
			String education, String occupation, String vehicle, String annual_income, String toilet,
			String water_source, String person_from_whom_you_seek_help, String development,
			String development_description, String whom_are_you_going_to_vote_for, String candidate_of_choice,
			String which_issue_are_you_facing_currently) {
		super();
		this.id = id;
		this.state = state;
		this.taluka = taluka;
		this.village = village;
		this.booth_No = booth_No;
		this.family_No_for_Booth = family_No_for_Booth;
		this.sr_No_for_HOF_in_VL = sr_No_for_HOF_in_VL;
		this.caste = caste;
		this.category = category;
		this.religion = religion;
		Sr_No_on_VL = sr_No_on_VL;
		this.name_of_voter = name_of_voter;
		this.age_of_voter = age_of_voter;
		this.mobile_number = mobile_number;
		this.adhar_no = adhar_no;
		this.education = education;
		this.occupation = occupation;
		this.vehicle = vehicle;
		this.annual_income = annual_income;
		this.toilet = toilet;
		this.water_source = water_source;
		this.person_from_whom_you_seek_help = person_from_whom_you_seek_help;
		this.development = development;
		this.development_description = development_description;
		this.whom_are_you_going_to_vote_for = whom_are_you_going_to_vote_for;
		this.candidate_of_choice = candidate_of_choice;
		this.which_issue_are_you_facing_currently = which_issue_are_you_facing_currently;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTaluka() {
		return taluka;
	}

	public void setTaluka(String taluka) {
		this.taluka = taluka;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getBooth_No() {
		return booth_No;
	}

	public void setBooth_No(String booth_No) {
		this.booth_No = booth_No;
	}

	public String getFamily_No_for_Booth() {
		return family_No_for_Booth;
	}

	public void setFamily_No_for_Booth(String family_No_for_Booth) {
		this.family_No_for_Booth = family_No_for_Booth;
	}

	public String getSr_No_for_HOF_in_VL() {
		return sr_No_for_HOF_in_VL;
	}

	public void setSr_No_for_HOF_in_VL(String sr_No_for_HOF_in_VL) {
		this.sr_No_for_HOF_in_VL = sr_No_for_HOF_in_VL;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getSr_No_on_VL() {
		return Sr_No_on_VL;
	}

	public void setSr_No_on_VL(String sr_No_on_VL) {
		Sr_No_on_VL = sr_No_on_VL;
	}

	public String getName_of_voter() {
		return name_of_voter;
	}

	public void setName_of_voter(String name_of_voter) {
		this.name_of_voter = name_of_voter;
	}

	public String getAge_of_voter() {
		return age_of_voter;
	}

	public void setAge_of_voter(String age_of_voter) {
		this.age_of_voter = age_of_voter;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getAdhar_no() {
		return adhar_no;
	}

	public void setAdhar_no(String adhar_no) {
		this.adhar_no = adhar_no;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getAnnual_income() {
		return annual_income;
	}

	public void setAnnual_income(String annual_income) {
		this.annual_income = annual_income;
	}

	public String getToilet() {
		return toilet;
	}

	public void setToilet(String toilet) {
		this.toilet = toilet;
	}

	public String getWater_source() {
		return water_source;
	}

	public void setWater_source(String water_source) {
		this.water_source = water_source;
	}

	public String getPerson_from_whom_you_seek_help() {
		return person_from_whom_you_seek_help;
	}

	public void setPerson_from_whom_you_seek_help(String person_from_whom_you_seek_help) {
		this.person_from_whom_you_seek_help = person_from_whom_you_seek_help;
	}

	public String getDevelopment() {
		return development;
	}

	public void setDevelopment(String development) {
		this.development = development;
	}

	public String getDevelopment_description() {
		return development_description;
	}

	public void setDevelopment_description(String development_description) {
		this.development_description = development_description;
	}

	public String getWhom_are_you_going_to_vote_for() {
		return whom_are_you_going_to_vote_for;
	}

	public void setWhom_are_you_going_to_vote_for(String whom_are_you_going_to_vote_for) {
		this.whom_are_you_going_to_vote_for = whom_are_you_going_to_vote_for;
	}

	public String getCandidate_of_choice() {
		return candidate_of_choice;
	}

	public void setCandidate_of_choice(String candidate_of_choice) {
		this.candidate_of_choice = candidate_of_choice;
	}

	public String getWhich_issue_are_you_facing_currently() {
		return which_issue_are_you_facing_currently;
	}

	public void setWhich_issue_are_you_facing_currently(String which_issue_are_you_facing_currently) {
		this.which_issue_are_you_facing_currently = which_issue_are_you_facing_currently;
	}

}