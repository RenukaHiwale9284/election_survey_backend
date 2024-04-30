package com.ElectionSurvey.Model;

public class Sentotponmail {

	
	private String mail_id;

	public Sentotponmail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Sentotponmail(String mail_id) {
		super();
		this.mail_id = mail_id;
	}

	@Override
	public String toString() {
		return "sentotponmail [mail_id=" + mail_id + "]";
	}

	public String getMail_id() {
		return mail_id;
	}

	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}
	
	
}
