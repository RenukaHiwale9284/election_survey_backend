package com.ElectionSurvey.Model;

public class Loginpojo {
	
	private String mail_id;
	
	private String username;
	
	private String password;

	public Loginpojo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Loginpojo(String mail_id, String username, String password) {
		super();
		this.mail_id = mail_id;
		this.username = username;
		this.password = password;
	}

	public String getMail_id() {
		return mail_id;
	}

	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
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
	
	
	
	

}
