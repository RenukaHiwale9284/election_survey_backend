package com.ElectionSurvey.Model;

public class SurveyForChooseCondidate {
private long id;
private String taluka;
private String village;
private String partyname;
private String candidatename;
private String why;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
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
public String getPartyname() {
	return partyname;
}
public void setPartyname(String partyname) {
	this.partyname = partyname;
}
public String getCandidatename() {
	return candidatename;
}
public void setCandidatename(String candidatename) {
	this.candidatename = candidatename;
}
public String getWhy() {
	return why;
}
public void setWhy(String why) {
	this.why = why;
}



}
